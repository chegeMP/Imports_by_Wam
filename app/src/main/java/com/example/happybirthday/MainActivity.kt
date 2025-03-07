package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import coil.compose.AsyncImage

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.R.drawable.teaser_1
import com.example.happybirthday.ui.theme.HappyBirthdayTheme
import coil.compose.rememberImagePainter
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppNavigation()
        }
    }
}
@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "teaser") {
        composable("teaser") { TeazerPage(navController) }
        composable("signup") { SignUpImage(navController) }
        composable("login") { LogInImage(navController) }
        composable("carlistingscreen") { CarListingScreen(navController) }
        composable("menuscreen") { MenuScreen(navController) }
        composable(
            "cardetailscreen/{carName}",
            arguments = listOf(navArgument("carName") { type = NavType.StringType })
        ) { backStackEntry ->
            val carName = backStackEntry.arguments?.getString("carName")
            val car = carList.find { it.name == carName }
            if (car != null) {
                CarDetailScreen(
                    car = car,
                    modifier = Modifier,
                    onBackClick = { navController.popBackStack() },
                    onPurchaseClick = { navController.navigate("paymentoptions") }
                )
            }
        }
        composable("paymentoptions") {
            PaymentOptionsScreen(
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        }
        composable("banktransfer") { BankTransferScreen(navController) } // Added Bank Transfer Screen
        composable("aboutus") { AboutUsScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("contactus") { ContactUsScreen(navController) }
        composable("reviews") { ReviewsScreen() }
        composable("stkpush") { STKPushPage() }
        composable("visapayment") { VisaPaymentPage(navController) }
        composable("mastercardpayment") { MastercardPaymentPage(navController) }
        composable("signoutconfirmation") {
            PageImage(
                message = "Are you sure you want to quit?",
                onYesClick = {
                    navController.navigate("login") {
                        popUpTo("teaser") { inclusive = true }
                    }
                },
                onNoClick = { navController.popBackStack() }
            )
        }
        composable("forgotpassword") { ForgotPasswordScreen(navController) }
        composable("verification") { VerificationScreen(navController) }
        composable("changepassword") { ChangePasswordScreen(navController) }
        composable("editprofile") { EditProfileScreen(navController) }
        composable("trackorder") { OrderTrackingScreen(navController, currentStep = 1) }
        composable("paymentplan") { PaymentPlanPage(navController) }
        composable("confirmation") { ConfirmationPage(navController) }
    }
}

@Composable
fun TeazerPage(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.teaser_1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Button(
            onClick = { navController.navigate("signup") }, // Navigate to SignUpImage
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .width(284.dp)
                .height(62.dp)
                .alpha(0.9f)
        ) {
            Text(
                text = "Get Started",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TeazerPagePreview() {
val navController = rememberNavController()
        TeazerPage( navController = navController)

}

@Composable
fun ReUseTextField(
    label: String,
    textValue: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = onTextChange,
        singleLine = true,
        label = { Text(label) },
        textStyle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Yellow
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Yellow,
            unfocusedTextColor = Color.Yellow,
            cursorColor = Color.Yellow,
            focusedContainerColor = Color.Black,
            unfocusedContainerColor = Color.Black,
            focusedBorderColor = Color.Yellow,
            unfocusedBorderColor = Color.Gray
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .width(300.dp)
            .height(50.dp)
    )
}

// Preview of the Reusable Text Field
@Preview(showBackground = true)
@Composable
fun ReUseTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    ReUseTextField(
        label = "Enter Name",
        textValue = text,
        onTextChange = { text = it }
    )
}

@Preview(showBackground = true)
@Composable
fun TextPreview() {
    Text(
        text = "How are you??",
        color = Color.Red,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun FilledButtonPreview() {
    FilledButton(title = "Sad", buttonWidth = 300, buttonHeight = 50)
}


@Composable
fun FilledButton(
    title: String,
    buttonWidth: Int,
    buttonHeight: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(Color.Black),
        modifier = modifier
    ) {
        Text(text = title, color = White, fontWeight = FontWeight.SemiBold, fontSize = 24.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpImagePreview() {
val navController = rememberNavController()
    SignUpImage( modifier = Modifier, navController = navController)
}


@Composable
fun PageImage(
    message: String,
    onYesClick: () -> Unit, // Add this parameter
    onNoClick: () -> Unit, // Add this parameter
    modifier: Modifier = Modifier
) {
    val image = painterResource(id = R.drawable.sub4) // Replace with your background image resource

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Translucent Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        // Confirmation Card
        Card(
            modifier = Modifier
                .width(354.dp)
                .height(274.dp)
                .wrapContentHeight()
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Buttons Row
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Yes Button
                    Button(
                        onClick = onYesClick, // Use the onYesClick parameter
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Yes",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // No Button
                    Button(
                        onClick = onNoClick, // Use the onNoClick parameter
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "No",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PageImagePreview() {
    PageImage(message = "Are you sure you want to quit?",
    onYesClick = {},
        onNoClick = {}
    )
}

@Composable
fun SignUpImage(navController: NavController, modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.signup)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Card(
            modifier = Modifier
                .width(361.dp)
                .height(550.dp) // Increased height to accommodate social buttons
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.7f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SIGN UP",
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var confirmPassword by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color.White) },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color.White) },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password", color = Color.White) },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        navController.navigate("login")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
                ) {
                    Text(text = "Sign Up", fontSize = 18.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Or Sign up with:", color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SocialLoginButton(R.drawable.facebook)
                    SocialLoginButton(R.drawable.instagram)
                    SocialLoginButton(R.drawable.google)

                }
            }
        }
    }
}

@Composable
fun SocialLoginButton(iconRes: Int) {
    IconButton(
        onClick = {},
        modifier = Modifier.size(50.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "Social Login",
            tint = Color.Unspecified,  // Fixes invisible icons
            modifier = Modifier.size(40.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun No() {
    FilledButton(title = "No", buttonWidth = 300, buttonHeight = 50)
}

@Composable
fun ReUseImage(painter: Painter) {
    Box() {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 1.0F,
            modifier = Modifier
                .width(412.dp)
                .height(917.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInImage(navController: NavController, modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.login)

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Semi-transparent login card
        Card(
            modifier = Modifier
                .width(320.dp)
                .height(500.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.6f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LOGIN",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                // Email Input Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.White
                    ),
                    textStyle = TextStyle(color = Color.White)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Password Input Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password", color = Color.White) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.White
                    ),
                    textStyle = TextStyle(color = Color.White)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Login Button
                Button(
                    onClick = {
                        navController.navigate("carlistingscreen") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
                ) {
                    Text(text = "Log In", fontSize = 18.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Forgot Password
                Text(
                    text = "Forgot password? Click here",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier.clickable {
                        // Navigate to the Forgot Password Screen
                        navController.navigate("forgotpassword")
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Sign In With Text
                Text(
                    text = "Or Sign in With:",
                    fontSize = 14.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Social Media Icons
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SocialIcon(R.drawable.facebook, "Facebook")
                    SocialIcon(R.drawable.instagram, "Instagram")
                    SocialIcon(R.drawable.google, "Google")
                }
            }
        }
    }
}

// Helper function for Social Media Icons
@Composable
fun SocialIcon(iconRes: Int, contentDesc: String) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = contentDesc,
        modifier = Modifier.size(40.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun LogInImagePreview() {
    val navController = rememberNavController()
    LogInImage(navController = navController, modifier = Modifier)
}


@Composable
fun PaymentOptionsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.payment),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Choose Payment Option:",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PaymentOptionButton(
                    name = "Mpesa",
                    logoResId = R.drawable.mpesa_logo,
                    onClick = { navController.navigate("stkpush") }
                )
                PaymentOptionButton(
                    name = "VISA",
                    logoResId = R.drawable.visa_logo,
                    onClick = { navController.navigate("visapayment") }
                )
                PaymentOptionButton(
                    name = "G Pay",
                    logoResId = R.drawable.gpay_logo,
                    onClick = { /* Handle G Pay payment */ }
                )
                PaymentOptionButton(
                    name = "Mastercard",
                    logoResId = R.drawable.mastercard_logo,
                    onClick = { navController.navigate("mastercardpayment") }
                )
                PaymentOptionButton(
                    name = "Bank Transfer",
                    logoResId = R.drawable.bank_transfer, // Add bank transfer logo
                    onClick = { navController.navigate("banktransfer") }
                )

            }
        }
    }
}

@Composable
fun PaymentOptionButton(
    name: String,
    logoResId: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = logoResId),
                contentDescription = "$name Logo",
                modifier = Modifier.size(40.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = name,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PaymentOptionsScreenPreview() {
    val navController = rememberNavController()
    PaymentOptionsScreen( navController = navController,onBackClick = {} )
}
@Composable
fun CarListingScreen(navController: NavController, modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") } // Holds the search input

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF121212))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Row with Menu, Greeting, and Profile Picture
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navController.navigate("menuscreen") }, // Navigate to MenuScreen
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Text(
                    text = "Good evening, Kasongo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFAF00),
                )

                Image(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { navController.navigate("profile") } // Navigate to ProfileScreen
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Single Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                placeholder = "Search here"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Filtered Car List
            val filteredCars = carList.filter { car ->
                // Check if any of the car's properties match the search query
                listOf(
                    car.name,
                    car.price,
                    car.fuelType,
                    car.location,
                    car.mileage
                ).any { it.contains(searchQuery, ignoreCase = true) }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(filteredCars) { car ->
                    CarCard(car = car, navController = navController) // Pass navController
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD3D3D3), shape = RoundedCornerShape(25.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
    }
}

@Composable
fun CarCard(car: Car, navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable { // Add clickable modifier
                navController.navigate("cardetailscreen/${car.name}") // Pass car name as argument
            },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Box {
            Image(
                painter = painterResource(id = car.imageRes),
                contentDescription = car.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 300f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = car.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Year of Manufacture: ${car.year}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Current Location: ${car.location}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Drive: ${car.drive}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Mileage: ${car.mileage}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Availability: ${car.availability}",
                    color = if (car.availability == "Available") Color.Green else Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color(0xFFFFAF00), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Ksh ${car.price}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = { /* TODO: Handle purchase */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFAF00)),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                enabled = car.availability == "Available" // Disable if not available
            ) {
                Text(text = "Purchase", color = Color.Black)
            }
        }
    }
}

data class Car(
    val name: String,
    val year: String,
    val location: String,
    val drive: String,
    val mileage: String,
    val price: String,
    val fuelType: String, // Add fuel type
    val imageRes: Int,
    val imageGallery: List<Int> = listOf(),
    val availability: String
)

val carList = listOf(
    Car(
        name = "Toyota RAV4",
        year = "2008",
        location = "Japan",
        drive = "4WD",
        mileage = "208,425km",
        price = "4,746,377",
        fuelType = "Petrol",
        imageRes = R.drawable.rav4,
        imageGallery = listOf(R.drawable.rav4, R.drawable.rav4_side, R.drawable.rav4_back, R.drawable.rav4_inside_f, R.drawable.rav4_inside_r, R.drawable.rav4_trunk, R.drawable.rav4_dash),
        availability = "Available"
    ),
    Car(
        name = "2019 McLaren 720s",
        year = "2019",
        location = "UK",
        drive = "RWD",
        mileage = "5,000km",
        price = "28,000,000",
        fuelType = "Petrol",
        imageRes = R.drawable.mclaren,
        imageGallery = listOf(R.drawable.mclaren, R.drawable.mclaren_side, R.drawable.mclaren_rear),
        availability = "Sold Out"
    ),
    Car(
        name = "2017 Audi A5",
        year = "2017",
        location = "Germany",
        drive = "AWD",
        mileage = "60,000km",
        price = "3,500,000",
        fuelType = "Diesel",
        imageRes = R.drawable.audi,
        imageGallery = listOf(R.drawable.audi, R.drawable.audi_front, R.drawable.audi_back),
        availability = "Available"
    ),
    Car(
        name = "2018 Mercedes-Benz AMG-GT",
        year = "2018",
        location = "Germany",
        drive = "RWD",
        mileage = "35,000km",
        price = "25,000,000",
        fuelType = "Petrol",
        imageRes = R.drawable.mercedes,
        imageGallery = listOf(R.drawable.mercedes, R.drawable.mercedes_side, R.drawable.mercedes_back),
        availability = "Sold Out"
    ),
    // Add Mercedes-Benz GLE350D here
    Car(
        name = "Mercedes-Benz GLE350D",
        year = "2020",
        location = "Germany",
        drive = "AWD",
        mileage = "30,000km",
        price = "15,000,000",
        fuelType = "Diesel",
        imageRes = R.drawable.gle350d, // Ensure you have this drawable resource
        imageGallery = listOf(R.drawable.gle350d, R.drawable.gle350_ff, R.drawable.gle350d_f,R.drawable.gle350d_r,R.drawable.gle350_if,R.drawable.gle350d_ir),
        availability = "Available"
    )
)


@Preview(showBackground = true)
@Composable
fun CarListingScreenPreview() {
    val navController = rememberNavController()
    CarListingScreen(navController = navController, modifier = Modifier)
}
@Preview(showBackground = true)
@Composable
fun CarDetailScreenPreview() {
    CarDetailScreen(car = carList[0],modifier = Modifier, onBackClick = {} , onPurchaseClick = {})
}

@Composable
fun CarDetailScreen(
    car: Car,
    modifier: Modifier,
    onBackClick: () -> Unit,
    onPurchaseClick: () -> Unit // Add this parameter
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1C)) // Dark Theme Background
    ) {
        // Top Navigation with Back Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        // Car Image Gallery
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(car.imageGallery) { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = car.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Car Details
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.carpage), // Replace with your actual image
                contentDescription = "Car Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
                    .alpha(0.2f) // Semi-transparent background
            )

            // Card on top of the background
            Card(
                shape = RoundedCornerShape(topStart = 50.dp, topEnd = 32.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(Color(0x802C2C2C)) // Semi-transparent background
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = car.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE4C300)
                    )
                    Text(
                        text = "Ksh ${car.price}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE4C300)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Vehicle Details",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White)

                    Spacer(modifier = Modifier.height(8.dp))

                    // Details List
                    val details = listOf(
                        "Year of Manufacture" to car.year,
                        "Current Location" to car.location,
                        "Availability" to car.availability,
                        "Drive" to car.drive,
                        "Mileage" to car.mileage
                    )
                    details.forEach { (title, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = title, color = Color.White)
                            Text(text = value, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Purchase Button
                    Button(
                        onClick = onPurchaseClick, // Use the onPurchaseClick parameter
                        colors = ButtonDefaults.buttonColors(Color(0xFFE4C300)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Purchase", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
@Composable
fun STKPushPage(onSendClick: () -> Unit = {}) {
    var pin by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF162F44), Color.Gray),
                tileMode = TileMode.Clamp,
            )),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text ="Do you wish to pay  Ksh 4,746,377 to Wamunyoro Importers Limited? A transaction charge of Ksh 1500 will be applied. "
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Enter M-Pesa PIN",
                    color = Color.Black
                )
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it },
                    label = { Text("PIN") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),


                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = { /* Cancel Action */ }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = onSendClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFAF00))
                    ) {
                        Text(text = "Send", color = Color.Black)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSTKPushPage() {
    STKPushPage()
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun VisaPaymentPage(
    navController: NavController,
    onCompleteClick: () -> Unit = {}
) {
    var cardNumber by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var expiryMonth by remember { mutableStateOf("") }
    var expiryYear by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF162F44), Color.Gray),
                tileMode = TileMode.Clamp,
            )),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                // Visa Logo
                Image(
                    painter = painterResource(id = R.drawable.visa),
                    contentDescription = "VISA Logo",
                    modifier = Modifier
                        .height(100.dp)
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                )

                // Card Number Field
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = { Text("Card Number", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = Color(0xFF0A1A2E),
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = Color(0xFF0A1A2E),
                        cursorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Expiry Date Fields
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = expiryMonth,
                        onValueChange = { expiryMonth = it },
                        label = { Text("Month", color = Color.White) },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedLabelColor = Color.White,
                            focusedLabelColor = Color(0xFF0A1A2E),
                            unfocusedBorderColor = Color.Gray,
                            focusedBorderColor = Color(0xFF0A1A2E),
                            cursorColor = Color.White
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = expiryYear,
                        onValueChange = { expiryYear = it },
                        label = { Text("Year", color = Color.White) },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedLabelColor = Color.White,
                            focusedLabelColor = Color(0xFF0A1A2E),
                            unfocusedBorderColor = Color.Gray,
                            focusedBorderColor = Color(0xFF0A1A2E),
                            cursorColor = Color.White
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // CVC Field
                OutlinedTextField(
                    value = cvc,
                    onValueChange = { cvc = it },
                    label = { Text("CVC", color = Color.White) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = Color(0xFF0A1A2E),
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = Color(0xFF0A1A2E),
                        cursorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Card Holder's Name Field
                OutlinedTextField(
                    value = cardHolder,
                    onValueChange = { cardHolder = it },
                    label = { Text("Card Holder's Name", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedLabelColor = Color.White,
                        focusedLabelColor = Color(0xFF0A1A2E),
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = Color(0xFF0A1A2E),
                        cursorColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Complete Order Button
                Button(
                    onClick = {
                        onCompleteClick()
                        navController.navigate("confirmation") // Navigate to confirmation page
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFAF00)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "COMPLETE ORDER KSH 4,746,377", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVisaPaymentPage() {
    val navController = rememberNavController()
    VisaPaymentPage(navController = navController)
}

@Composable
fun ConfirmationPage(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF162F44), Color.Gray),
                    tileMode = TileMode.Clamp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = null,
                    modifier = Modifier.width(500.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Confirmation",
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Your purchase was successful!",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        modifier = Modifier,
                        onClick = {
                            navController.navigate("trackorder") // Navigate to OrderTrackingScreen
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFAF00))
                    ) {
                        Text(
                            text = "Done",
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewConfirmationPage() {
    val navController = rememberNavController()
    ConfirmationPage(navController= navController)
}

@Composable
fun MenuScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
    ) {
        Image(
            painter = painterResource(id = R.drawable.menupage),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(250.dp)
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) { // Navigate back
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Menu",
                    color = Color(0xFFFFA726),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(24.dp))
            MenuItem(text = "Track Order", onClick = { navController.navigate("trackorder") })
            MenuItem(text = "Payment Plan", onClick = { navController.navigate("paymentplan") }) // Navigate to Payment Plan
            MenuItem(text = "About Us", onClick = { navController.navigate("aboutus") }) // Navigate to About Us
            MenuItem(text = "Contact Us", onClick = { navController.navigate("contactus") }) // Navigate to Contact Us
            MenuItem(text = "Reviews", onClick = { navController.navigate("reviews") }) // Navigate to Reviews
            MenuItem(text = "Sign Out", onClick = { navController.navigate("signoutconfirmation") }) // Navigate to Sign Out Confirmation
        }
    }
}
@Composable
fun MenuItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = Color(0xFFFFA726),
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick)
    )
}
@Preview
@Composable
fun MenuScreenPreview() {
    val navController = rememberNavController()
    MenuScreen( navController = navController)
}

@Composable
fun AboutUsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF162F44), Color.Gray),
                tileMode = TileMode.Clamp,
            ))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { // Navigate back
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = "About Us",
                color = Color(0xFFFFA726),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(testimonials) { testimonial ->
                TestimonialCard(testimonial)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

data class Testimonial(val text: String, val image: Int, val name: String)

val testimonials = listOf(
    Testimonial(
        "Founded over 30 years ago, we have 3 decades of experience in the imports industry.",
        R.drawable.car_image,
        ""
    ),
    Testimonial(
        "100+ staff ready and willing to serve you at your convenience with utmost professionalism.",
        R.drawable.staff_image,
        ""
    ),
    Testimonial(
        "100% proven track record on timely and quality delivery.",
        R.drawable.delivery_image,
        ""
    ),
    Testimonial(
        "Their delivery process was smooth, staff respect their customers, and their services are 5 star rated.",
        R.drawable.lisa_image,
        "Lisa"
    ),
    Testimonial(
        "I imported and received my car on time, their prices are good.",
        R.drawable.kasongo_image,
        "Kasongo"
    )
)

@Composable
fun TestimonialCard(testimonial: Testimonial) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C)),

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = testimonial.text,
                color = Color.White,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = testimonial.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "--${testimonial.name}",
                    color = Color.Yellow,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AboutUsScreenPreview() {
    val navController = rememberNavController()
    AboutUsScreen(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
    ) {
        Image(
            painter = painterResource(id = R.drawable.vw_logo),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Card(
            modifier = Modifier
                .height(600.dp)
                .width(344.dp)
                .align(Alignment.Center)
                .background(Color.Black.copy(alpha = 0.5f))
                .alpha(0.7f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "What's the Feeling?",
                    color = Color(0xFFFFA726),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                StarRatingBar()
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Comment...", color = Color.White) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(300.dp)
                        .height(300.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Black,
                        containerColor = Color.DarkGray,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Submit Action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
                ) {
                    Text(text = "Submit", color = Color.White)
                }
            }
        }
    }
}
@Composable
fun StarRatingBar() {
    var rating by remember { mutableStateOf(0) }
    Row {
        (1..5).forEach { index ->
            Icon(
                imageVector = if (index <= rating) Icons.Default.Star else Icons.Default.Star,
                contentDescription = "Star",
                tint = Color.Yellow,
                modifier = Modifier
                    .size(36.dp)
                    .clickable { rating = index }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewsScreenPreview() {
    ReviewsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF162F44), Color.Gray),
                tileMode = TileMode.Clamp,
            ))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Forgot Password?",
            color = Color(0xFFFFAF00),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.sad_user),
            contentDescription = "Sad Avatar",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enter the email registered to your account",
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We will send you a link to reset your password",
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Enter your email address", color = Color.Gray) },
            textStyle = TextStyle(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFFAF00),
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Navigate to the Verification Screen
                navController.navigate("verification")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFAF00))
        ) {
            Text("Send", color = Color.White)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    val navController = rememberNavController()
    ForgotPasswordScreen(navController = navController )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(navController: NavController) { // Add NavController parameter
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF162F44), Color.Gray),
                tileMode = TileMode.Clamp,
            ))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Verification",
            color = Color(0xFFFFAF00),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.happy_user),
            contentDescription = "Happy Avatar",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enter the verification code we just sent to your email",
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(4) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.width(50.dp),
                    textStyle = TextStyle(textAlign = TextAlign.Center, color = Color.White),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFFFAF00),
                        unfocusedBorderColor = Color.Gray
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Navigate to the Change Password Screen
                navController.navigate("changepassword")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFAF00))
        ) {
            Text("Verify", color = Color.White)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun VerificationScreenPreview() {
    val navController = rememberNavController()
    VerificationScreen(navController = navController)
}

@Composable
fun OrderTrackingScreen(navController: NavController, currentStep: Int) {
    val steps = listOf(
        "Order Placed", "Payment Confirmed", "Car Shipped", "Arrived at the Port of Mombasa", "Cleared Customs", "Ready for Pickup/Delivery"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF162F44), Color.Gray),
                    tileMode = TileMode.Clamp
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Track Your Vehicle",
            color = Color(0xFFFFA726),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        steps.forEachIndexed { index, step ->
            StepItem(step, index < currentStep, index == currentStep - 1)
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Thank you For Your Trust",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFA726)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Box(modifier = Modifier) {
            Button(
                onClick = {
                    // Navigate to Car Listing Page
                    navController.navigate("carlistingscreen") {
                        popUpTo("teaser") { inclusive = false } // Clear the back stack up to teaser
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA500),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Done")
            }
        }
    }
}


@Composable
fun StepItem(title: String, isCompleted: Boolean, isCurrent: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = if (isCompleted) R.drawable.done else R.drawable.done),
            contentDescription = null,
            tint = if (isCompleted) Color.Green else Color.Gray,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = if (isCurrent) Color(0xFFFFA726) else Color.White,
            fontSize = 18.sp,
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OrderTrackingScreenPreview() {
    val navController = rememberNavController()
    OrderTrackingScreen(navController = navController, currentStep = 3)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.tire_background), // Add your background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar with Back Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) { // Navigate back
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f)) // Pushes text to center

                Text(
                    text = "Contact Us",
                    style = TextStyle(
                        color = Color(0xFFFFA500),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f)) // Balances row layout
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Contact Information
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "wamunyoro@gmail.com",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "+254700000000",
                    color = Color(0xFFFFA500),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Username Field
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.Gray),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Gray,
                    focusedBorderColor = Color(0xFFFFA500),
                    cursorColor = Color.White,
                    focusedLabelColor = Color(0xFFFFA500),
                    unfocusedLabelColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.Gray),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Gray,
                    focusedBorderColor = Color(0xFFFFA500),
                    cursorColor = Color.White,
                    focusedLabelColor = Color(0xFFFFA500),
                    unfocusedLabelColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Message Field
            OutlinedTextField(
                value = "",
                onValueChange = {},
                shape = RoundedCornerShape(20.dp),
                label = { Text("Write your message here...") },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(150.dp)
                    .background(Color.Gray),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Gray,
                    focusedBorderColor = Color(0xFFFFA500),
                    cursorColor = Color.White,
                    focusedLabelColor = Color(0xFFFFA500),
                    unfocusedLabelColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Message Button
            Button(
                onClick = { /* TODO: Send Message */ },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA500),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Message")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactUsScreenPreview() {
    val navController = rememberNavController()
    ContactUsScreen(navController = navController)
}


@Composable
fun ProfileScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF162F44), Color.Gray),
                        tileMode = TileMode.Clamp,
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Profile",
                    color = Color(0xFFFFA500),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1.5f))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Image with Edit Icon
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                IconButton(
                    onClick = { navController.navigate("editprofile") },
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.Create, contentDescription = "Edit Profile", tint = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Jonathan Kasongo",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Details Card
            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.3f)), // FIXED
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // FIXED
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // ID Number
                    Column {
                        Text(text = "ID Number", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        BasicTextField(
                            value = TextFieldValue("40000000"),
                            onValueChange = {},
                            textStyle = TextStyle(color = Color(0xFFE0E0E0), fontSize = 16.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.DarkGray.copy(alpha = 0.5f), CircleShape)
                                .padding(8.dp)
                        )
                    }

                    // Date of Birth
                    Column {
                        Text(text = "Date of Birth", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        BasicTextField(
                            value = TextFieldValue("1-5-2000"),
                            onValueChange = {},
                            textStyle = TextStyle(color = Color(0xFFE0E0E0), fontSize = 16.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.DarkGray.copy(alpha = 0.5f), CircleShape)
                                .padding(8.dp)
                        )
                    }

                    // Phone Number
                    Column {
                        Text(text = "Phone Number", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        BasicTextField(
                            value = TextFieldValue("+254748000000"),
                            onValueChange = {},
                            textStyle = TextStyle(color = Color(0xFFE0E0E0), fontSize = 16.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.DarkGray.copy(alpha = 0.5f), CircleShape)
                                .padding(8.dp)
                        )
                    }

                    // Location
                    Column {
                        Text(text = "Location", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        BasicTextField(
                            value = TextFieldValue("Nairobi"),
                            onValueChange = {},
                            textStyle = TextStyle(color = Color(0xFFE0E0E0), fontSize = 16.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.DarkGray.copy(alpha = 0.5f), CircleShape)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPeview() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF162F44), Color.Gray),
                    tileMode = TileMode.Clamp
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            // Back Arrow (Now properly aligned to the left)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Edit Profile",
                    color = Color(0xFFFFA500),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center, // Centers text
                    modifier = Modifier
                        .fillMaxWidth() // Makes the text take full width
                        .padding(top = 16.dp) // Optional padding for better positioning
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Image Selection
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable { launcher.launch("image/*") }
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.profile_user),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.add_photos),
                    contentDescription = "Change Profile Photo",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Change Profile Photo", color = Color(0xFFE0E0E0), fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Form Fields
            val textFieldModifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.Transparent)

            val textFieldColors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFFFFA500),
                unfocusedIndicatorColor = Color.Gray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.White,
                disabledTextColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )


            OutlinedTextField(
                value = "First Name",
                onValueChange = {},
                label = { Text("First Name", color = Color.White) },
                colors = textFieldColors,
                modifier = textFieldModifier
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "Last Name",
                onValueChange = {},
                label = { Text("Last Name", color = Color.White) },
                colors = textFieldColors,
                modifier = textFieldModifier
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "Date of Birth",
                onValueChange = {},
                label = { Text("Date of Birth", color = Color.White) },
                colors = textFieldColors,
                modifier = textFieldModifier
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "Identification Number",
                onValueChange = {},
                label = { Text("Identification Number", color = Color.White) },
                colors = textFieldColors,
                modifier = textFieldModifier
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "Phone Number",
                onValueChange = {},
                label = { Text("Phone Number", color = Color.White) },
                colors = textFieldColors,
                modifier = textFieldModifier
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "Location",
                onValueChange = {},
                label = { Text("Location", color = Color.White) },
                colors = textFieldColors,
                modifier = textFieldModifier
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Save Changes", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    val navController = rememberNavController()
    EditProfileScreen(navController = navController)
}


@Composable
fun PaymentPlanPage(navController: NavController) { // Add NavController as a parameter
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF162F44), Color.Gray),
                tileMode = TileMode.Clamp,
            )),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Payment Plan",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                PaymentSection(title = "Payment Methods", content = "- Mobile Money (M-Pesa, Airtel Money)\n- Bank Transfers\n- Credit/Debit Cards (Visa, Mastercard, AMEX)\n- Cryptocurrency (Optional)")
                PaymentSection(title = "Installment Plan", content = "- Initial Deposit: 30% (Reserves the car)\n- Second Installment: 40% (After shipping confirmation)\n- Final Payment: 30% (Upon arrival for vehicle release)")
                PaymentSection(title = "Transaction Costs & Fees", content = "- Processing Fees: 1.5% for card payments\n- M-Pesa Transaction Fee: Ksh. 150 per transaction\n- Bank Transfer Fee: As per bank charges")
                PaymentSection(title = "Security & Verification", content = "- Secure Payment Gateway with SSL encryption\n- OTP Verification for secure transactions\n- E-Receipts & Confirmation Emails")
                PaymentSection(title = "Refund & Cancellation Policy", content = "- Full refund if cancellation occurs before shipping\n- 20% deduction for cancellations after shipping\n- No refund once the vehicle is registered")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("menuscreen") }, // Navigate to MenuScreen
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
                    modifier = Modifier.fillMaxWidth())
                {
                    Text(text = "Done", color = Color.Black)
                }
            }
        }
    }
}
@Composable
fun PaymentSection(title: String, content: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(
            text = title,
            color = Color.Yellow,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp
        )
        Text(
            text = content,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
@Preview( showBackground = true)
@Composable
fun PaymentPlanPagePreview() {
    val navController = rememberNavController()
    PaymentPlanPage(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class) // For experimental APIs
@Composable
fun MastercardPaymentPage(
    navController: NavController, // Add NavController parameter
    onCompleteClick: () -> Unit = {}
) {
    var cardNumber by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var expiryMonth by remember { mutableStateOf("") }
    var expiryYear by remember { mutableStateOf("") }

    // Load the background image
    val backgroundImage = painterResource(id = R.drawable.pay) // Replace with your image resource

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // Fallback background color
        contentAlignment = Alignment.Center
    ) {
        // Background Image
        Image(
            painter = backgroundImage,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Adjust the image scaling
        )

        // Payment Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.8f)), // Semi-transparent dark gray
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                // Mastercard Logo
                Image(
                    painter = painterResource(id = R.drawable.mastercard_logo), // Replace with your Mastercard logo resource
                    contentDescription = "Mastercard Logo",
                    modifier = Modifier
                        .height(50.dp)
                        .padding(bottom = 16.dp)
                )

                // Card Number Field
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = { Text("Card Number", color = Color.White) }, // White placeholder text
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color(0xFFEB001B), // Mastercard red for focused placeholder
                        unfocusedLabelColor = Color.White, // White placeholder when unfocused
                        focusedBorderColor = Color(0xFFEB001B), // Mastercard red for focused border
                        unfocusedBorderColor = Color.Gray, // Gray border when unfocused
                        cursorColor = Color.White // White cursor
                    ),
                    textStyle = TextStyle(color = Color.White), // White text color
                    placeholder = { Text("Card Number", color = Color.White) } // White placeholder text
                )

                // Expiry Date Fields
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = expiryMonth,
                        onValueChange = { expiryMonth = it },
                        label = { Text("Month", color = Color.White) }, // White placeholder text
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedLabelColor = Color(0xFFEB001B), // Mastercard red for focused placeholder
                            unfocusedLabelColor = Color.White, // White placeholder when unfocused
                            focusedBorderColor = Color(0xFFEB001B), // Mastercard red for focused border
                            unfocusedBorderColor = Color.Gray, // Gray border when unfocused
                            cursorColor = Color.White // White cursor
                        ),
                        textStyle = TextStyle(color = Color.White), // White text color
                        placeholder = { Text("MM", color = Color.White) } // White placeholder text
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = expiryYear,
                        onValueChange = { expiryYear = it },
                        label = { Text("Year", color = Color.White) }, // White placeholder text
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedLabelColor = Color(0xFFEB001B), // Mastercard red for focused placeholder
                            unfocusedLabelColor = Color.White, // White placeholder when unfocused
                            focusedBorderColor = Color(0xFFEB001B), // Mastercard red for focused border
                            unfocusedBorderColor = Color.Gray, // Gray border when unfocused
                            cursorColor = Color.White // White cursor
                        ),
                        textStyle = TextStyle(color = Color.White), // White text color
                        placeholder = { Text("YY", color = Color.White) } // White placeholder text
                    )
                }

                // CVC Field
                OutlinedTextField(
                    value = cvc,
                    onValueChange = { cvc = it },
                    label = { Text("CVC", color = Color.White) }, // White placeholder text
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color(0xFFEB001B), // Mastercard red for focused placeholder
                        unfocusedLabelColor = Color.White, // White placeholder when unfocused
                        focusedBorderColor = Color(0xFFEB001B), // Mastercard red for focused border
                        unfocusedBorderColor = Color.Gray, // Gray border when unfocused
                        cursorColor = Color.White // White cursor
                    ),
                    textStyle = TextStyle(color = Color.White), // White text color
                    placeholder = { Text("CVC", color = Color.White) } // White placeholder text
                )

                // Card Holder's Name Field
                OutlinedTextField(
                    value = cardHolder,
                    onValueChange = { cardHolder = it },
                    label = { Text("Card Holder's Name", color = Color.White) }, // White placeholder text
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color(0xFFEB001B), // Mastercard red for focused placeholder
                        unfocusedLabelColor = Color.White, // White placeholder when unfocused
                        focusedBorderColor = Color(0xFFEB001B), // Mastercard red for focused border
                        unfocusedBorderColor = Color.Gray, // Gray border when unfocused
                        cursorColor = Color.White // White cursor
                    ),
                    textStyle = TextStyle(color = Color.White), // White text color
                    placeholder = { Text("Card Holder's Name", color = Color.White) } // White placeholder text
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Complete Order Button
                Button(
                    onClick = {
                        onCompleteClick()
                        navController.navigate("confirmation") // Navigate to confirmation page
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)), // Mastercard red
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "COMPLETE ORDER KSH 4,746,377", color = Color.White)
                }
            }
        }
    }
}

@Preview( showBackground = true)
@Composable
fun MastercardPaymentPagePreview() {
    val navController = rememberNavController()
    MastercardPaymentPage( navController = navController)
}




@Composable
fun OtpVerificationPage(
    email: String = "user@example.com", // ✅ Explicit type annotation
    onOtpVerified: () -> Unit = {}, // ✅ Explicit type annotation
    onResendOtp: () -> Unit = {} // ✅ Explicit type annotation
) {
    var otp by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()
        .background(brush = Brush.verticalGradient(
            colors = listOf(Color(0xFF162F44), Color.Gray),
            tileMode = TileMode.Clamp,
        )), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .width(320.dp)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter OTP",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "A 6-digit OTP has been sent to $email",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            // OTP Input Field
            OutlinedTextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("Enter OTP") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Error message (if OTP is incorrect)
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Verify OTP Button
            Button(
                onClick = {
                    if (otp == "123456") { // Replace with backend validation
                        onOtpVerified()
                    } else {
                        errorMessage = "Invalid OTP. Please try again."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
            ) {
                Text(text = "Verify OTP", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resend OTP Button
            TextButton(onClick = onResendOtp) {
                Text(text = "Resend OTP", color = Color.Blue, fontSize = 16.sp)
            }
        }
    }
}

@Preview( showBackground = true)
@Composable
fun OtpVerificationPagePreview() {
    OtpVerificationPage()
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(navController: NavController) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF162F44), Color.Gray),
                    tileMode = TileMode.Clamp,
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Change Password",
            color = Color(0xFFFFAF00),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.happy_user), // Use an appropriate image
            contentDescription = "Change Password Avatar",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // New Password Input Field
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFFAF00),
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm New Password Input Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm New Password", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFFAF00),
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                // Handle password change logic
                if (newPassword == confirmPassword) {
                    // Passwords match, proceed with password change
                    // Example: Call an API or update local state
                    navController.navigate("login") { // Navigate back to login after successful change
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    // Show an error message (e.g., using a Snackbar or Toast)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFAF00))
        ) {
            Text(text = "Submit", fontSize = 18.sp, color = Color.White)
        }
    }
}
@Preview( showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    val navController = rememberNavController()
    ChangePasswordScreen(navController = navController)
}







@Composable
fun BankTransferScreen(navController: NavController) {
Column(modifier = Modifier
    .background(
        brush = Brush.verticalGradient(
            colors = listOf(Color(0xFF162F44), Color.Gray),
            tileMode = TileMode.Clamp,
        )
    )
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = "Bank Transfer Details",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Bank Name: ABC Bank")
        Text(text = "Account Number: 1234567890")
        Text(text = "Branch: Nairobi")
        Text(text = "Swift Code: ABCDEF123")
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFAF00))
        ) {
            Text("Back to Payment Options")
        }
    }
}
}

@Preview( showBackground = true)
@Composable
fun BankTransferScreenPreview(){
    val navController = rememberNavController()
    BankTransferScreen(navController = navController)
}
