import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
	id("org.jetbrains.kotlin.jvm")
	id("application")
}

group = "com.github.hummel"
version = LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd"))

val embed: Configuration by configurations.creating

dependencies {
	embed("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
	embed("com.formdev:flatlaf:3.2.5")
	embed("com.formdev:flatlaf-intellij-themes:3.2.5")
	implementation("com.formdev:flatlaf:3.2.5")
	implementation("com.formdev:flatlaf-intellij-themes:3.2.5")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(8)
	}
}

application {
	mainClass = "com.github.hummel.it.lab1.MainKt"
}

tasks {
	named<JavaExec>("run") {
		standardInput = System.`in`
	}
	jar {
		manifest {
			attributes(
				mapOf(
					"Main-Class" to "com.github.hummel.it.lab1.MainKt"
				)
			)
		}
		from(embed.map {
			if (it.isDirectory) it else zipTree(it)
		})
		duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	}
}
