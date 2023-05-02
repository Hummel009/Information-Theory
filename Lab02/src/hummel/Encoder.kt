package hummel

import java.io.File
import kotlin.experimental.xor
import kotlin.math.pow


class Encoder(
	private var polynomialPowers: IntArray,
	private var initialKey: String,
	private var pathToSrcFile: String,
	private var pathToResFile: String,
	private var gui: GUI
) {

	fun encode() {
		val reg = Register(polynomialPowers, initialKey)
		val srcBytes = File(pathToSrcFile).readBytes()

		val bites = 8
		val bufSrcFile = StringBuilder()
		val bufGenkey = StringBuilder()
		val bufResFile = StringBuilder()
		for (i in srcBytes.indices) {
			bufSrcFile.append(Integer.toBinaryString(srcBytes[i].toInt() and 0xFF).format("%8s", "0") + " ")

			val currKey = StringBuilder()
			repeat(bites) {
				currKey.append(reg.generateKeyBit())
			}

			var keyByte = 0

			for (j in currKey.toString().indices) {
				val bp = (currKey.toString()[j].digitToInt()).toByte() * 2.0.pow((bites - 1 - j)).toInt().toByte()
				keyByte = (keyByte + bp)
			}

			bufGenkey.append(currKey.toString())
			srcBytes[i] = srcBytes[i] xor keyByte.toByte()
			bufResFile.append(Integer.toBinaryString(srcBytes[i].toInt() and 0xFF).format("%8s", "0") + " ")
		}
		File(pathToResFile).writeBytes(srcBytes)
		gui.srcFileBin = bufSrcFile.toString().replace(" ", "")
		gui.keyStream = bufGenkey.toString().replace(" ", "")
		gui.resFileBin = bufResFile.toString().replace(" ", "")
	}
}