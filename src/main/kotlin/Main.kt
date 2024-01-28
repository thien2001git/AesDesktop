import Icon.rememberDelete
import Icon.rememberEditDocument
import Icon.rememberKey
import Utils.AesUtils
import Utils.FileUtils
import Utils.FileUtils.DECRYPT_FOLDER
import Utils.FileUtils.ENCRYPT_FOLDER
import Utils.FileUtils.INPUT
import Utils.FileUtils.KEY_FOLDER
import Utils.FileUtils.OUTPUT
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.FileDialog
import java.io.File
import java.nio.file.*
import java.security.Key
import javax.crypto.BadPaddingException

@Composable
@Preview
fun App() {

    FileUtils.deleteFiles("$ENCRYPT_FOLDER$OUTPUT")
    FileUtils.deleteFiles("$DECRYPT_FOLDER$OUTPUT")

    var listKey by remember { mutableStateOf(FileUtils.listFile(KEY_FOLDER)?.toList()) }

    var listSource by remember { mutableStateOf(FileUtils.listFile("$ENCRYPT_FOLDER$INPUT")?.toList()) }
    var listEcrypted by remember { mutableStateOf(FileUtils.listFile("$ENCRYPT_FOLDER$OUTPUT")?.toList()) }

    var listSource1 by remember { mutableStateOf(FileUtils.listFile("$DECRYPT_FOLDER$INPUT")?.toList()) }
    var listDcrypted by remember { mutableStateOf(FileUtils.listFile("$DECRYPT_FOLDER$OUTPUT")?.toList()) }

    var selectedFile by remember { mutableStateOf(File("")) }
    var showDialog = remember { mutableStateOf(false) }
    val NOT_KEY = "Selected file is not a key!"
    val WRONG_KEY = "Selected file is not key of this document!"
    var alertMessage by remember { mutableStateOf(NOT_KEY) }
    MaterialTheme {
        Row {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(300.dp)) {

                Text(text = "AES KEY")
                Spacer(modifier = Modifier.height(10.dp))
                listKey = FileUtils.listFile(KEY_FOLDER)?.toList()
                ListKey(listKey!!, onImport = {
                    val list = openFileDialog(ComposeWindow(), "Open key file")
                    list.forEach {
                        val desFile = File("$KEY_FOLDER${System.currentTimeMillis()}.key")
                        Files.copy(it.toPath(), desFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                    }
                    listKey = FileUtils.listFile(KEY_FOLDER)?.toList()
                }, onDelete = {
                    listKey = FileUtils.listFile(KEY_FOLDER)?.toList()
                }) {
                    selectedFile = it
                }
            }

            //ENCRYPT

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(300.dp)) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "ENCRYPT")
                Text(text = "Selected key: ")
                Text(text = selectedFile.name)

                Button(onClick = {
                    val list = openFileDialog(ComposeWindow(), "Open Source file")
                    list.forEach {
                        val desFile = File("$ENCRYPT_FOLDER$INPUT${System.currentTimeMillis()}_en_input_${it.name}")
                        Files.copy(it.toPath(), desFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                    }
                    listSource = FileUtils.listFile("$ENCRYPT_FOLDER$INPUT")?.toList()
                }) {
                    Text(text = "Import A Source")
                }
                Column(modifier = Modifier.height(100.dp).verticalScroll(rememberScrollState(0))) {
                    listSource?.forEach {
                        FileItem(rememberEditDocument(), it, Color.White, onDelete = {
                            listSource = FileUtils.listFile("$ENCRYPT_FOLDER$INPUT")?.toList()
                        }) {

                        }
                    }
                }

                Button(onClick = {
                    var key = FileUtils.loadObjectFromBinary(selectedFile.path)
                    if (key == null) {
                        showDialog.value = true
                    } else {
                        key = key as Key
                        listSource!!.forEach {
                            val bytes = Files.readAllBytes(it.toPath())
                            val encryptBytes = AesUtils.encryptAES(bytes, key)
                            val path: Path = Paths.get("$ENCRYPT_FOLDER$OUTPUT${it.name}")
                            Files.write(path, encryptBytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
                        }

                        listEcrypted = FileUtils.listFile("$ENCRYPT_FOLDER$OUTPUT")?.toList()
                    }
                }) {
                    Text(text = "RUN")
                }
                Column {
                    listEcrypted!!.forEach {
                        FileItem(rememberEditDocument(), it, Color.White, onDelete = {}) {

                        }
                    }
                }

            }


            //DECRYPT
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(300.dp)) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "DECRYPT")
                Text(text = "Selected key: ")
                Text(text = selectedFile.name)

                Button(onClick = {
                    val list = openFileDialog(ComposeWindow(), "Open Source file")
                    list.forEach {
                        val desFile = File("$DECRYPT_FOLDER$INPUT${System.currentTimeMillis()}_de_input_${it.name}")
                        Files.copy(it.toPath(), desFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                    }
                    listSource1 = FileUtils.listFile("$DECRYPT_FOLDER$INPUT")?.toList()
                }) {
                    Text(text = "Import A Source")
                }
                Column(modifier = Modifier.height(100.dp).verticalScroll(rememberScrollState(0))) {
                    listSource1?.forEach {
                        FileItem(rememberEditDocument(), it, Color.White, onDelete = {
                            listSource1 = FileUtils.listFile("$DECRYPT_FOLDER$INPUT")?.toList()
                        }) {

                        }
                    }
                }

                Button(onClick = {
                    val key = FileUtils.loadObjectFromBinary(selectedFile.path) as Key
                    if (key == null) {
                        showDialog.value = true
                    } else {
                        listSource1!!.forEach {
                            val bytes = Files.readAllBytes(it.toPath())
                            try {
                                val decryptBytes = AesUtils.decryptAES(bytes, key)
                                val path: Path = Paths.get("$DECRYPT_FOLDER$OUTPUT${it.name}")
                                Files.write(path, decryptBytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
                            } catch (e: BadPaddingException) {
                                showDialog.value = true
                            }


                        }

                        listDcrypted = FileUtils.listFile("$DECRYPT_FOLDER$OUTPUT")?.toList()
                    }
                }) {
                    Text(text = "RUN")
                }
                Column {
                    listDcrypted!!.forEach {
                        FileItem(rememberEditDocument(), it, Color.White, onDelete = {}) {

                        }
                    }
                }

            }
        }

        if (showDialog.value) {
            AlertDialog(onDismissRequest = {
                showDialog.value = false
            }, title = {
                Text("Warning!")
            }, text = {
                Text(alertMessage)
            }, buttons = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = {
                        showDialog.value = false
                    }) {
                        Text("OK")
                    }
                }
            })
        }
    }
}

@Composable
fun ListKey(
    listKey: List<File>,
    onImport: () -> Unit,
    onDelete: () -> Unit,
    selectedFile: (file: File) -> Unit,
) {
    var selected = remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            val _key = AesUtils.generateAESKey()
            FileUtils.saveObjectAsBinary("$KEY_FOLDER${System.currentTimeMillis()}.key", _key)
            onDelete()
        }) {
            Text(text = "Generate AES key")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onImport) {
            Text(text = "Import A key")
        }

        listKey.forEach { it ->
            val color = if (selected.value == it.name) {
                Color.Yellow
            } else {
                Color.White
            }
            Spacer(modifier = Modifier.height(10.dp))
            FileItem(rememberKey(), it, color, onDelete) {
                selected.value = it.name
                selectedFile(it)
            }
        }
    }
}

@Composable
fun FileItem(myIcon: ImageVector, file: File, color: Color, onDelete: () -> Unit, selectedFile: (file: File) -> Unit) {

    var isNewWindowOpen by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(color = color, shape = RoundedCornerShape(20.dp)).fillMaxWidth().clickable {
            selectedFile(file)
            isNewWindowOpen = true
        }) {
        Icon(imageVector = myIcon, contentDescription = null)
        Spacer(Modifier.width(10.dp))
        Text(file.name.substring(0, 16))
        Spacer(Modifier.width(10.dp))
        Icon(imageVector = rememberDelete(), contentDescription = null, modifier = Modifier.clickable {
            file.delete()
            onDelete()
        })
    }

    if (isNewWindowOpen) {
        openNewWindow(file) {
            isNewWindowOpen = false
        }
    }
}


fun openFileDialog(
    window: ComposeWindow, title: String, allowMultiSelection: Boolean = true
): Set<File> {
    return FileDialog(window, title, FileDialog.LOAD).apply {
        isMultipleMode = allowMultiSelection
        isVisible = true
    }.files.toSet()
}

@Composable
fun openNewWindow(file: File, onClose: () -> Unit) {
    Window(onCloseRequest = onClose, title = file.name) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).height(600.dp)) {
            Text(
                text = "File Name: ${file.name}",
                fontWeight = FontWeight(600),
                fontSize = TextUnit(60f, type = TextUnitType(10))
            )
            val fileContent = file.readText()
            Text(text = "Content:", fontWeight = FontWeight(600))
            val end = if (fileContent.length > 5000) {
                5000
            } else {
                fileContent.length
            }
            Text(text = fileContent.substring(0, end))
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
