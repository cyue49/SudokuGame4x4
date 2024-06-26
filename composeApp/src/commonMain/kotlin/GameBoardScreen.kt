package org.example.sudokugame4x4

import CompletionScreen
import HomeScreen
import SudokuGrid
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import gameLogic.SudokuViewModel
import timer

data class GameBoardScreen(
    val username: String ,
    val viewModel: SudokuViewModel = SudokuViewModel()
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var navigateToCompletion by remember { mutableStateOf(false) }
        var completionTime by remember { mutableStateOf("") }

        // Observe if Sudoku is complete
        LaunchedEffect(viewModel.isSudokuComplete) {
            if (viewModel.isSudokuComplete) {
                completionTime = viewModel.getSolvingTime() ?: "00:00"
                navigateToCompletion = true
            }
        }

        if (navigateToCompletion) {
            navigator.push(CompletionScreen(
                username = username,
                time = completionTime,
                onNewGame = {
                    navigator.popUntil { it is HomeScreen }
                    navigator.push(GameBoardScreen(username = username))
                },
                onHome = {
                    navigator.popUntil { it is HomeScreen }
                }
            ))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFD700))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Welcome message and timer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hi $username!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2878FF)
                )
                if(!viewModel.validateMove()){
                    Text(
                        text = "Check your moves!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
                timer(viewModel)
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 4x4 Sudoku board
            SudokuGrid(viewModel)

            Spacer(modifier = Modifier.height(40.dp))

            // Number buttons from 1 to 4

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (i in 1..4) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                when (i) {
                                    1 -> Color(0xFF59D5E0)  // Green
                                    2 -> Color(0xFFFFEB3B)  // Yellow
                                    3 -> Color(0xFFFAA300)  // Orange
                                    4 -> Color(0xFFF4538A)  // Pink
                                    else -> Color.Gray
                                }, shape = CircleShape
                            )
                            .border(2.dp, Color.Black, CircleShape)
                            .clickable {
                                viewModel.updateCell(i)
                                       },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$i",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))

            // Utility buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButtonWithIcon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear",
                    onClick = { viewModel.updateCell(0) }
                )
                IconButtonWithIcon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    onClick = { viewModel.clearAllCells() }
                )
                IconButtonWithIcon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refresh",
                    onClick = { viewModel.getNewGame() }
                )
                IconButtonWithIcon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Erase",
                    onClick = { navigator.popUntil { it is HomeScreen } }
                )
            }
        }
    }
}

@Composable
fun IconButtonWithIcon(imageVector: ImageVector, contentDescription: String?, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector, contentDescription, modifier = Modifier.size(24.dp))
    }
}
