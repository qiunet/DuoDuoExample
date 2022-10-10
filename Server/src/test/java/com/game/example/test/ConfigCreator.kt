package com.game.example.test

import com.game.example.common.logger.GameLogger
import org.eclipse.jgit.api.Git
import org.qiunet.flash.handler.util.proto.GeneratorProtoFeature
import org.qiunet.flash.handler.util.proto.GeneratorProtoFile
import org.qiunet.flash.handler.util.proto.ProtoGeneratorModel
import org.qiunet.flash.handler.util.proto.ProtobufVersion
import org.qiunet.utils.config.anno.DConfigInstance
import org.qiunet.utils.config.conf.DHocon
import org.qiunet.utils.data.ArgsData
import org.qiunet.utils.exceptions.CustomException
import org.qiunet.utils.scanner.ClassScanner
import org.qiunet.utils.scanner.ScannerType
import org.qiunet.utils.string.StringUtil
import org.qiunet.utils.system.CmdUtil
import java.io.File
import java.io.FilenameFilter
import java.io.IOException
import java.util.function.Consumer

/***
 *
 *
 * qiunet
 * 2021/9/23 15:01
 */
object ConfigCreator {
    @DConfigInstance("privacy.conf")
    private var config: DHocon? = null

    // 存放ai.config 和 all_in_one.proto的git目录
    private const val CONFIG_GIT_OUTPUT_DIR = "config_git_output_dir"

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        ClassScanner.getInstance(ScannerType.GENERATOR_PROTO, ScannerType.CREATE_AI_CONFIG, ScannerType.FILE_CONFIG)
            .scanner("com.game.example")
        val svnDir = config!!.getString(GeneratorProtoFile.PROTO_OUTPUT_DIR)

//        svnCommit(svnDir) {
			GeneratorProtoFile.generator(
				File(svnDir), ProtoGeneratorModel.GROUP_BY_MODULE,
                ArgsData.of(GeneratorProtoFeature.PROTO_PACKAGE, ProtobufVersion.DEFAULT_PACKAGE_NAME),
			)
//		}

      //  生成DTools 需要的配置.
		val gitDit = config?.getString(CONFIG_GIT_OUTPUT_DIR)
//        gitCommit(gitDit) {
  //			CreateAiConfig.generator(File(gitDit, "ai"));

			GeneratorProtoFile.generator(
				File(gitDit, "proto"), ProtoGeneratorModel.ALL_IN_ONE,
                ArgsData.of(GeneratorProtoFeature.OUTPUT_PROTOCOL_LIST_ENUM, ""),
                ArgsData.of(GeneratorProtoFeature.OUTPUT_PROTOCOL_MAPPING_MD, "")
			)
//		}
	}

    @Throws(IOException::class)
    internal fun svnUpdate(dir: String?) {
        val result = CmdUtil.exec("svn", "update", dir)
        GameLogger.COMM_LOGGER.info(result)
    }

    @Throws(IOException::class)
    internal fun svnCommit(dir: String?, runnable: Runnable) {
		svnUpdate(dir);

		runnable.run()

		val result = CmdUtil.exec("svn", "status", dir)
        val strings = StringUtil.split(result, "\n")
        for (string in strings) {
            val split = StringUtil.split(string, " ")
            if (split[0].trim { it <= ' ' } == "?") {
                CmdUtil.exec("svn", "add", split[split.size - 1])
            }
        }
        if (strings.isNotEmpty()) {
            GameLogger.COMM_LOGGER.info(CmdUtil.exec("svn", "commit", dir, "-m 'program_auto_commit。。。。。。'"))
        }
    }

    private fun gitCommit(dir: String?, create: Runnable) {
		if (dir == null) {
			throw CustomException("dir is empty!")
		}
        gitCommit(File(dir), create)
    }

    private fun gitCommit(dir: File, create: Runnable) {
        if (!dir.exists()) {
            dir.mkdirs()
        }
        if (!dir.isDirectory) {
            throw CustomException("Not a directory")
        }
        try {
            Git.open(gitHome(dir)).use { git ->
                if (!git.pull().call().isSuccessful) {
                    GameLogger.COMM_LOGGER.error("Git pull fail!")
                    return
                }

				create.run()

                val status = git.status().call()
                if (status.isClean) {
                    GameLogger.COMM_LOGGER.error("Git file have not change!")
                    return
                }
                val add = git.add()
                status.changed.forEach(Consumer { filePattern: String? -> add.addFilepattern(filePattern) })
                status.added.forEach(Consumer { filePattern: String? -> add.addFilepattern(filePattern) })
                status.removed.forEach(Consumer { filePattern: String? -> add.addFilepattern(filePattern) })
                status.modified.forEach(Consumer { filePattern: String? -> add.addFilepattern(filePattern) })
                add.call()
                git.commit().setMessage("auto commit!").call()
                git.push().call()
                GameLogger.COMM_LOGGER.info("Git push success!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun gitHome(dir: File?): File? {
        if (dir == null) {
            throw NullPointerException()
        }
        val files = dir.listFiles(FilenameFilter { _: File?, name: String -> ".git" == name })
        if (files != null && files.isNotEmpty()) {
            return files[0]
        }
        val parentFile = dir.parentFile ?: return null
        return gitHome(parentFile)
    }
}
