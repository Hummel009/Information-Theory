package hummel

class Vigenere(private var msg: String, private var key: String, private var gui: GUI) {
	fun encode(): String {
		var newKey = key + msg
		newKey = newKey.dropLast(key.length)
		var encryptMsg = ""

		for (x in msg.indices) {
			val q = gui.alphabet.length
			val mn = gui.alphabet.indexOf(msg[x])
			val kn = gui.alphabet.indexOf(newKey[x])
			encryptMsg += gui.alphabet[(q + mn + kn) % q]
		}
		return encryptMsg
	}

	fun decode(): String {
		val currentKey = StringBuilder(key)

		for (x in msg.indices) {
			val q = gui.alphabet.length
			val mn = gui.alphabet.indexOf(msg[x])
			val kn = gui.alphabet.indexOf(currentKey[x])
			currentKey.append(gui.alphabet[(q + mn - kn) % q])
		}
		return currentKey.substring(key.length).toString()
	}
}