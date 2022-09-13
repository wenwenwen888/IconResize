import net.coobird.thumbnailator.Thumbnails
import java.io.File
import java.io.IOException
import java.util.*


class IconResize {

    companion object {

        //Android图标需要的尺寸
        private val MIPMAP_SIZE = mutableListOf(192, 144, 96, 72, 48)
        private val MIPMAP_FOLDER =
            arrayOf("mipmap-xxxhdpi", "mipmap-xxhdpi", "mipmap-xhdpi", "mipmap-hdpi", "mipmap-mdpi")

        @JvmStatic
        fun main(args: Array<String>) {

            val sc = Scanner(System.`in`)
            println("=====请输入图片路径：")
            //这是输入的路径
            val srcPath = sc.nextLine()
            //获取输入路径的文件夹路径
            val srcFolderPath = srcPath.substring(0, srcPath.lastIndexOf("\\") + 1)

            println("=====是否需要Android尺寸，不输入则需要(直接回车)，输入任意字符则不需要：")
            //这是输入的是否需要Android尺寸
            val isNeedAndroidStr = sc.nextLine()
            val isNeedAndroid = isNeedAndroidStr.isEmpty()

            println("=====输入其余尺寸，不输入则不需要(直接回车)，直接输入数字(多个请英文逗号隔开)：")
            //这是输入额外尺寸
            val otherSizeStr = sc.nextLine()
            //不为空字符就处理
            if (otherSizeStr.isNotEmpty()) {
                val otherSize = otherSizeStr.split(",")
                for (size in otherSize) {
                    MIPMAP_SIZE.add(size.toInt())
                }
            }

            val androidFolder = srcFolderPath + "android\\"
            //输入路径的文件夹创建android文件夹
            if (!File(androidFolder).exists()) {
                File(androidFolder).mkdirs()
            }

            //开始修改尺寸和创建相对应的文件
            for (i in MIPMAP_SIZE.indices) {
                var newFolder = androidFolder
                var newPicName = "${MIPMAP_SIZE[i]}x${MIPMAP_SIZE[i]}.png"
                //判断是否需要Android尺寸，并且i小于默认的尺寸，创建相对应文件夹
                if (isNeedAndroid && i < MIPMAP_FOLDER.size) {
                    newFolder = androidFolder + MIPMAP_FOLDER[i] + "\\"
                    File(newFolder).mkdirs()
                    newPicName = "ic_launcher.png"
                }
                val destPath = newFolder + newPicName
                //判断是否不需要Android尺寸，并且i小于默认的尺寸，则跳出此循环
                if (!isNeedAndroid && i < MIPMAP_FOLDER.size){
                    continue
                }
                resizeImage(srcPath, destPath, MIPMAP_SIZE[i], MIPMAP_SIZE[i])
            }

        }

        /**
         * 修改尺寸
         */
        @Throws(IOException::class)
        fun resizeImage(srcPath: String, destPath: String, newWith: Int, newHeight: Int) {
            try {
                Thumbnails.of(srcPath).forceSize(newWith, newHeight).toFile(destPath)
                println("${newWith}尺寸修改成功！")
            } catch (e: Exception) {
                println("${newWith}尺寸修改失败:$e")
            }
        }

    }

}