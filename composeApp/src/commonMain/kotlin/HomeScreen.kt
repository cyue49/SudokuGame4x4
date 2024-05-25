import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gameLogic.SudokuViewModel
import org.example.sudokugame4x4.GameBoardScreen

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val name = remember { mutableStateOf("") }
        var showRules by remember { mutableStateOf(false) }
        val viewModel: SudokuViewModel = SudokuViewModel()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFD700))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sudoku 4x4",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2878FF)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Player's Name:") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color(0xFF2878FF),
                    unfocusedBorderColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navigator.push(GameBoardScreen(name.value, viewModel)) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2878FF)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(50.dp)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Play",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showRules = !showRules },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2878FF)),  // Updated color
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(50.dp)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Game Rules",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = showRules) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Game Rules",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "1. Each row, column, and 2x2 grid must contain the numbers 1-4 without repetition.")
                        Text(text = "2. Use logical reasoning to fill in the missing numbers.")
                        Text(text = "3. The game is won when all squares are correctly filled.")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showRules = false }) {
                        Text("Close")
                    }
                }
            }
        }
    }
}