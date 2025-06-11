package app.junsu.junjanote.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.junsu.junjanote.common.ui.Destination
import app.junsu.junjanote.common.ui.SelectableDestinationIcon
import coil3.compose.AsyncImage
import junjanote.composeapp.generated.resources.Res
import junjanote.composeapp.generated.resources.aboutMe_route_name
import junjanote.composeapp.generated.resources.home_route_name
import junjanote.composeapp.generated.resources.posts_route_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val (selectedRoute, onSelectRoute) = remember {
        mutableStateOf(MainDestinations.HOME)
    }

    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Row(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
        ) {
            MainNavigationRail(
                selectedRoute = selectedRoute,
                onSelectRoute = onSelectRoute,
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                LazyColumn(
                    modifier = Modifier.widthIn(max = 1000.dp).fillMaxHeight(),
                    horizontalAlignment = Alignment.Start,
                    contentPadding = PaddingValues(
                        top = 32.0.dp,
                        bottom = 32.0.dp,
                    ),
                ) {
                    item {
                        AsyncImage(
                            model = "https://upload.wikimedia.org/wikipedia/commons/6/61/San_Francisco_from_the_Marin_Headlands_in_August_2022.jpg",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(512.dp).clip(shape = RoundedCornerShape(24.dp)),
                            onError = {
                                it.result.throwable.printStackTrace()
                            },
                        )
                    }
                    item {
                        Text(
                            text = "Junsu\nPark",
                            style = TextStyle(fontSize = 128.sp),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    items(count = 15) { index ->
                        Text(
                            "HIHI $index",
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MainNavigationRail(
    selectedRoute: MainDestinations,
    onSelectRoute: (MainDestinations) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
        header = {
            FloatingActionButton(
                onClick = {

                },
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    // TODO
                    contentDescription = null,
                )
            }
        },
    ) {
        MainDestinations.entries.forEach { destination ->
            val selected = destination == selectedRoute
            NavigationRailItem(
                selected = selected,
                onClick = { onSelectRoute(destination) },
                label = { Text(text = destination.text) },
                icon = {
                    Icon(
                        imageVector = destination.icon.run {
                            if (selected) {
                                selectedIcon
                            } else {
                                unselectedIcon
                            }
                        },
                        contentDescription = destination.text,
                    )
                },
            )
        }
    }
}


enum class MainDestinations(
    override val route: String,
    override val icon: SelectableDestinationIcon,
) : Destination {
    HOME(
        route = "home",
        icon = SelectableDestinationIcon(
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
        ),
    ),
    POSTS(
        route = "posts",
        icon = SelectableDestinationIcon(
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
            selectedIcon = Icons.AutoMirrored.Filled.List,
        ),
    ),
    ABOUT_ME(
        route = "about-me",
        icon = SelectableDestinationIcon(
            unselectedIcon = Icons.Outlined.Info,
            selectedIcon = Icons.Filled.Info,
        ),
    ),
    ;
}

private val MainDestinations.text: String
    @Composable
    get() = stringResource(
        resource = when (this) {
            MainDestinations.HOME -> Res.string.home_route_name
            MainDestinations.POSTS -> Res.string.posts_route_name
            MainDestinations.ABOUT_ME -> Res.string.aboutMe_route_name
        },
    )
