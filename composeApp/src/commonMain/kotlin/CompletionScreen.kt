import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

data class CompletionScreen(
    val time: String,
    val onNewGame: () -> Unit,
    val onHome: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFD700))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Excellent!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2878FF)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF176), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Time: $time",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF66BB6A)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNewGame,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2878FF)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(50.dp)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "New Game",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onHome,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00E5FF)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(50.dp)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Home",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
