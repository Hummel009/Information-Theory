package hummel

import java.util.*

class ColumnMethod(private var msg: String, private var key: String, private var gui: GUI) {
	private val square = Array(msg.length + 3) { Array(key.length) { " " } }

	fun decode(show: Boolean): String {
		fillDecryptTable()
		if (show) {
			showTable()
		}
		return scanDecryptTable()
	}

	fun encode(show: Boolean): String {
		fillEncryptTable()
		if (show) {
			showTable()
		}
		return scanEncryptTable()
	}

	private fun scanEncryptTable(): String {
		sortTable(1)
		val sb = StringBuilder()
		for (i in key.indices) {
			for (j in 3 until msg.length + 3) {
				if (square[j][i] != " ") {
					sb.append(square[j][i])
				}
			}
		}
		return sb.toString()
	}

	private fun scanDecryptTable(): String {
		val sb = StringBuilder()
		for (i in 3 until msg.length + 3) {
			for (j in key.indices) {
				if (square[i][j] != " ") {
					sb.append(square[i][j])
				}
			}
		}
		return sb.toString()
	}

	private fun fillEncryptTable() {
		prepareTable()

		var currentPos = 0
		var currentLine = 3
		var currentRule = 1

		loop@ while (true) {
			for (i in key.indices) {
				square[currentLine][i] = msg[currentPos].toString()
				currentPos++
				if (currentPos >= msg.length) {
					break@loop
				}
				if (currentRule == key.length + 1) {
					currentRule = 1
				}
				if (square[1][i] == currentRule.toString()) {
					currentLine++
					currentRule++
					break
				}
			}
		}
	}

	private fun fillDecryptTable() {
		prepareTable()

		var currentPos = 1
		var currentLine = 3
		var currentRule = 1

		loop@ while (true) {
			for (i in key.indices) {
				if (currentPos > msg.length) {
					break@loop
				}
				if (currentRule == key.length + 1) {
					currentRule = 1
				}
				square[currentLine][i] = "*"
				currentPos++
				if (square[1][i] == currentRule.toString()) {
					currentLine++
					currentRule++
					break
				}
			}
		}
		sortTable(1)

		var count = 0
		for (j in key.indices) {
			for (i in 3 until msg.length + 3) {
				if (square[i][j] == "*") {
					square[i][j] = msg[count].toString()
					count++
				}
			}
		}
		sortTable(2)
	}

	private fun prepareTable() {
		for (i in key.indices) {
			square[0][i] = key[i].toString()
			square[1][i] = gui.alphabet.indexOf(square[0][i]).toString()
			square[2][i] = (i + 1).toString()
		}

		val sortable = IntArray(key.length)
		val usedIDs = HashSet<Int>()
		for (i in key.indices) {
			sortable[i] = square[1][i].toInt()
		}

		Arrays.sort(sortable)

		for (i in key.indices) {
			var newID = (sortable.indexOf(square[1][i].toInt()) + 1)
			if (!usedIDs.contains(newID)) {
				square[1][i] = newID.toString()
				usedIDs.add(newID)
			} else {
				newID++
				square[1][i] = newID.toString()
				usedIDs.add(newID)
			}
		}
	}

	private fun sortTable(row: Int) {
		for (i in 0 until square[0].size - 1) {
			var minIndex = i
			for (j in i + 1 until square[0].size) {
				if (square[row][j] < square[row][minIndex]) {
					minIndex = j
				}
			}
			if (minIndex != i) {
				for (k in square.indices) {
					val temp = square[k][i]
					square[k][i] = square[k][minIndex]
					square[k][minIndex] = temp
				}
			}
		}
	}

	private fun showTable() {
		for (i in square.indices) {
			var hasAtLeastOneLetter = false
			for (j in square[0].indices) {
				if (square[i][j] != " ") {
					print(square[i][j] + " ")
					hasAtLeastOneLetter = true
				}
			}
			if (hasAtLeastOneLetter) {
				println()
			}
		}
	}
}