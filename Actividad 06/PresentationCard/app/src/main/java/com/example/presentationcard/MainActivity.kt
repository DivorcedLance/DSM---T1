package com.example.presentationcard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentationcard.ui.theme.PresentationCardTheme
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Github
import compose.icons.simpleicons.Linkedin
import androidx.core.net.toUri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PresentationCardTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PresentationCardModern()
                }
            }
        }
    }
}

@Composable
fun PresentationCardModern() {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            colorResource(R.color.gradient_start),
            colorResource(R.color.gradient_end)
        )
    )

    val context = LocalContext.current
    val githubUrl = stringResource(R.string.url_github)
    val linkedinUrl = stringResource(R.string.url_linkedin)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(20.dp)
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = cardColors(containerColor = colorResource(R.color.card_bg)),
            elevation = cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(108.dp)
                        .shadow(elevation = 6.dp, shape = CircleShape, clip = false)
                        .clip(CircleShape)
                        .background(colorResource(R.color.avatar_bg)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.android_logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(88.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.full_name),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.text_primary),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = stringResource(R.string.job_title),
                    fontSize = 16.sp,
                    color = colorResource(R.color.text_secondary),
                    modifier = Modifier.padding(top = 4.dp)
                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(0.85f),
                    thickness = DividerDefaults.Thickness,
                    color = colorResource(R.color.divider)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ContactItem(
                        leading = {
                            Icon(
                                imageVector = SimpleIcons.Github,
                                contentDescription = "GitHub",
                                tint = colorResource(R.color.icon_tint)
                            )
                        },
                        text = stringResource(R.string.github)
                    )
                    ContactItem(
                        leading = {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Phone",
                                tint = colorResource(R.color.icon_tint)
                            )
                        },
                        text = stringResource(R.string.phone)
                    )
                    ContactItem(
                        leading = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = colorResource(R.color.icon_tint)
                            )
                        },
                        text = stringResource(R.string.email)
                    )
                }

                Spacer(Modifier.height(18.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledTonalButton(
                        onClick = { openUrl(context, githubUrl) },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = colorResource(R.color.tonal_btn_bg)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = SimpleIcons.Github,
                            contentDescription = "GitHub",
                            tint = colorResource(R.color.tonal_btn_icon)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "GitHub",
                            color = colorResource(R.color.tonal_btn_text)
                        )
                    }
                    FilledTonalButton(
                        onClick = { openUrl(context, linkedinUrl) },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = colorResource(R.color.tonal_btn_bg)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = SimpleIcons.Linkedin,
                            contentDescription = "LinkedIn",
                            tint = colorResource(R.color.tonal_btn_icon)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "LinkedIn",
                            color = colorResource(R.color.tonal_btn_text)
                        )
                    }
                }
            }
        }
    }
}

private fun openUrl(context: android.content.Context, url: String) {
    val normalized = if (url.startsWith("http://") || url.startsWith("https://")) url
    else "https://$url"
    val intent = Intent(Intent.ACTION_VIEW, normalized.toUri())
    context.startActivity(intent)
}

@Composable
private fun ContactItem(
    leading: @Composable () -> Unit,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.size(22.dp),
            contentAlignment = Alignment.Center
        ) {
            leading()
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(R.color.text_primary)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPresentationCardModern() {
    PresentationCardTheme {
        PresentationCardModern()
    }
}