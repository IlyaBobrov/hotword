package com.asprog.hotword.navigation.lobby

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asprog.hotword.R
import com.asprog.hotword.navigation.controller.NavRouts
import com.asprog.hotword.ui.theme.HotWordTheme

@Composable
fun LobbyScreen(
    navigate: (NavRouts) -> Unit
) {
    val createGame = { navigate(NavRouts.FromLobby.ToCreateGame) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.padding(vertical = 32.dp),
                style = MaterialTheme.typography.displayLarge.merge(TextStyle(fontWeight = FontWeight.Bold)),
                color = MaterialTheme.colorScheme.secondary
            )
            Button(onClick = createGame) {
                Text(text = "Начать игру")
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SBlockChangePreview() {
    HotWordTheme {
        LobbyScreen {}
    }
}
