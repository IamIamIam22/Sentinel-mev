package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SentinelRepository
import com.example.ui.components.ContractLifecycleTab
import com.example.ui.components.FaucetAutomatorTab
import com.example.ui.components.MevBotTab
import com.example.ui.components.SmartWalletTab
import com.example.ui.components.ProfileTab
import com.example.ui.theme.*

enum class TabType {
    MEV, LIFECYCLE, FAUCET, WALLETS, PROFILE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    repository: SentinelRepository,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf(TabType.MEV) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(CosmicBackground),
        topBar = {
            Column {
                // Top App Bar
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Immersive UI Gradient Logo Badge
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        androidx.compose.ui.graphics.Brush.linearGradient(
                                            colors = listOf(SentinelGold, SentinelBlue)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Security,
                                    contentDescription = "Logo",
                                    tint = CosmicBackground,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "SENTINEL MEV",
                                color = CosmicTextPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = 1.5.sp
                            )
                        }
                    },
                    actions = {
                        val isRealMode by repository.isRealMainnetMode
                        val context = LocalContext.current
                        
                        // Mainnet Real/Simulated Toggle Switch Badge
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isRealMode) SentinelGoldDim else CosmicCardInner)
                                .border(1.dp, if (isRealMode) SentinelGold else CosmicDivider, RoundedCornerShape(20.dp))
                                .clickable {
                                    repository.saveMainnetMode(!isRealMode)
                                    val newMode = if (!isRealMode) "Real Mainnet Mode" else "Simulated Sandbox Mode"
                                    Toast.makeText(context, "Switched operation mode to: $newMode", Toast.LENGTH_SHORT).show()
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(if (isRealMode) SentinelGold else CosmicTextDim)
                            )
                            Text(
                                text = if (isRealMode) "MAINNET ACTIVE" else "SANDBOX SIM",
                                color = if (isRealMode) SentinelGold else CosmicTextSecondary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        // Connection Status Tag
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(SentinelEmeraldDim)
                                .border(1.dp, SentinelEmerald, RoundedCornerShape(20.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(SentinelEmerald)
                            )
                            Text(
                                text = "RPC ACTIVE",
                                color = SentinelEmerald,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = CosmicBackground,
                        titleContentColor = CosmicTextPrimary
                    )
                )

                // High fidelity Hero Banner using generated image asset
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .border(1.dp, CosmicDivider, RoundedCornerShape(24.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_sentinel_hero),
                        contentDescription = "Sentinel Banner",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Transparent overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(androidx.compose.ui.graphics.Color(0xBB0F0D13))
                    )
                    
                    // Banner text overlay
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "AUTOMATED MEMPOOL COMMAND",
                            color = SentinelGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Cross-Chain Arbitrage & Liquidity Protection Studio",
                            color = CosmicTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Decentralized Faucets, Contract Lifecycles, and Multi-chain Daemons",
                            color = CosmicTextSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        },
        bottomBar = {
            // Standard Navigation Bar styled with active pills
            NavigationBar(
                containerColor = CosmicSurface,
                tonalElevation = 8.dp,
                windowInsets = WindowInsets.navigationBars,
                modifier = Modifier.border(BorderStroke(1.dp, CosmicDivider))
            ) {
                NavigationBarItem(
                    selected = activeTab == TabType.MEV,
                    onClick = { activeTab = TabType.MEV },
                    icon = { Icon(Icons.Filled.Hub, contentDescription = "MEV Bots") },
                    label = { Text("MEV Bots", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CosmicBackground,
                        selectedTextColor = SentinelGold,
                        indicatorColor = SentinelGold,
                        unselectedIconColor = CosmicTextSecondary,
                        unselectedTextColor = CosmicTextSecondary
                    )
                )
                NavigationBarItem(
                    selected = activeTab == TabType.LIFECYCLE,
                    onClick = { activeTab = TabType.LIFECYCLE },
                    icon = { Icon(Icons.Filled.ViewInAr, contentDescription = "Contracts") },
                    label = { Text("Lifecycles", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CosmicBackground,
                        selectedTextColor = SentinelGold,
                        indicatorColor = SentinelGold,
                        unselectedIconColor = CosmicTextSecondary,
                        unselectedTextColor = CosmicTextSecondary
                    )
                )
                NavigationBarItem(
                    selected = activeTab == TabType.FAUCET,
                    onClick = { activeTab = TabType.FAUCET },
                    icon = { Icon(Icons.Filled.WaterDrop, contentDescription = "Faucet Automator") },
                    label = { Text("Faucets", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CosmicBackground,
                        selectedTextColor = SentinelGold,
                        indicatorColor = SentinelGold,
                        unselectedIconColor = CosmicTextSecondary,
                        unselectedTextColor = CosmicTextSecondary
                    )
                )
                NavigationBarItem(
                    selected = activeTab == TabType.WALLETS,
                    onClick = { activeTab = TabType.WALLETS },
                    icon = { Icon(Icons.Filled.Key, contentDescription = "Smart Wallets") },
                    label = { Text("Smart Wallets", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CosmicBackground,
                        selectedTextColor = SentinelGold,
                        indicatorColor = SentinelGold,
                        unselectedIconColor = CosmicTextSecondary,
                        unselectedTextColor = CosmicTextSecondary
                    )
                )
                NavigationBarItem(
                    selected = activeTab == TabType.PROFILE,
                    onClick = { activeTab = TabType.PROFILE },
                    icon = { Icon(Icons.Filled.SyncLock, contentDescription = "Profile Sync") },
                    label = { Text("Profile Sync", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CosmicBackground,
                        selectedTextColor = SentinelGold,
                        indicatorColor = SentinelGold,
                        unselectedIconColor = CosmicTextSecondary,
                        unselectedTextColor = CosmicTextSecondary
                    )
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        // Render content inside crossfade for smooth screen transitioning
        Crossfade(
            targetState = activeTab,
            animationSpec = tween(250),
            modifier = Modifier
                .fillMaxSize()
                .background(CosmicBackground)
                .padding(innerPadding)
        ) { tab ->
            when (tab) {
                TabType.MEV -> {
                    MevBotTab(
                        repository = repository,
                        onNavigateToLifecycle = { activeTab = TabType.LIFECYCLE }
                    )
                }
                TabType.LIFECYCLE -> {
                    ContractLifecycleTab(
                        repository = repository
                    )
                }
                TabType.FAUCET -> {
                    FaucetAutomatorTab(
                        repository = repository
                    )
                }
                TabType.WALLETS -> {
                    SmartWalletTab(
                        repository = repository
                    )
                }
                TabType.PROFILE -> {
                    ProfileTab(
                        repository = repository
                    )
                }
            }
        }

        // Interactive Opportunity Alert Dialog popup
        val activeOpportunity = repository.activeOpportunityPrompt.value
        if (activeOpportunity != null) {
            androidx.compose.ui.window.Dialog(
                onDismissRequest = {
                    repository.activeOpportunityPrompt.value = null
                },
                properties = androidx.compose.ui.window.DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                var isExecuting by remember { mutableStateOf(false) }
                var isFinished by remember { mutableStateOf(false) }
                var executionSuccess by remember { mutableStateOf(false) }
                val consoleLogs = remember { mutableStateListOf<String>() }
                val scope = rememberCoroutineScope()

                val defaultPool = when (activeOpportunity.asset) {
                    "WBTC" -> "Aave V3 Capital Pool"
                    "ETH", "WETH" -> "Aave V3 Capital Pool"
                    "USDC", "USDT" -> "Balancer V2 Vault (Zero-Fee)"
                    "LINK" -> "Uniswap V3 Flash-swap"
                    else -> "Balancer V2 Vault (Zero-Fee)"
                }
                var selectedPool by remember { mutableStateOf(defaultPool) }
                var showPoolDropdown by remember { mutableStateOf(false) }

                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CosmicSurface),
                    border = BorderStroke(2.dp, if (isFinished && executionSuccess) SentinelEmerald else SentinelGold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Title Header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = if (isFinished) {
                                    if (executionSuccess) Icons.Filled.CheckCircle else Icons.Filled.Error
                                } else Icons.Filled.NotificationsActive,
                                contentDescription = "Alert",
                                tint = if (isFinished) {
                                    if (executionSuccess) SentinelEmerald else Color(0xFFEF5350)
                                } else SentinelGold,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = if (isFinished) "EXECUTION RESULT" else "MEV PATH DETECTED",
                                color = CosmicTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        if (!isExecuting && !isFinished) {
                            // Opportunity specs
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(CosmicCardInner)
                                    .border(1.dp, CosmicDivider, RoundedCornerShape(12.dp))
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Asset Pathway:", color = CosmicTextSecondary, fontSize = 12.sp)
                                    Text(
                                        text = "${activeOpportunity.asset} (Arbitrage)",
                                        color = SentinelGold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Routing DEX:", color = CosmicTextSecondary, fontSize = 12.sp)
                                    Text(
                                        text = "${activeOpportunity.buyDex} ➔ ${activeOpportunity.sellDex}",
                                        color = CosmicTextPrimary,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Profit Target:", color = CosmicTextSecondary, fontSize = 12.sp)
                                    Text(
                                        text = String.format("+%.2f%% ($%.2f)", activeOpportunity.priceDiffPct, activeOpportunity.estProfitUsd),
                                        color = SentinelEmerald,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Est. Gas Subsidies:", color = CosmicTextSecondary, fontSize = 12.sp)
                                    Text(
                                        text = String.format("$%.2f Gas", activeOpportunity.requiredGasUsd),
                                        color = SentinelBlue,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }

                            // Interactive Borrowing Pool Selector
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(CosmicCardInner)
                                    .border(1.dp, CosmicDivider, RoundedCornerShape(12.dp))
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Icon(
                                            imageVector = Icons.Filled.AccountBalance,
                                            contentDescription = "Pool",
                                            tint = SentinelEmerald,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "Borrowing Capital Pool",
                                            color = CosmicTextPrimary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                    
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(CosmicBackground)
                                            .clickable { showPoolDropdown = !showPoolDropdown }
                                            .border(1.dp, CosmicDivider, RoundedCornerShape(8.dp))
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(
                                                text = selectedPool,
                                                color = SentinelGold,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace
                                            )
                                            Icon(
                                                imageVector = if (showPoolDropdown) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                                contentDescription = "Expand",
                                                tint = CosmicTextSecondary,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                }

                                if (showPoolDropdown) {
                                    val availablePools = listOf(
                                        "Aave V3 Capital Pool",
                                        "Balancer V2 Vault (Zero-Fee)",
                                        "Uniswap V3 Flash-swap",
                                        "dYdX SoloMargin"
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        availablePools.forEach { pool ->
                                            val isSelected = pool == selectedPool
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(if (isSelected) CosmicBackground else Color.Transparent)
                                                    .clickable {
                                                        selectedPool = pool
                                                        showPoolDropdown = false
                                                    }
                                                    .padding(8.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = pool,
                                                    color = if (isSelected) SentinelEmerald else CosmicTextSecondary,
                                                    fontSize = 11.sp,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                                )
                                                if (isSelected) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Check,
                                                        contentDescription = "Selected",
                                                        tint = SentinelEmerald,
                                                        modifier = Modifier.size(12.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Text(
                                text = "A lucrative arbitrage opportunity has been intercepted by your background daemons. Would you like to ride the financials directly on-chain via the actual mainnet application, or simulate the sandbox run?",
                                color = CosmicTextSecondary,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )

                            // Action Buttons
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                Button(
                                    onClick = {
                                        isExecuting = true
                                        consoleLogs.clear()
                                        repository.executeArbitrageOpportunity(
                                            opportunity = activeOpportunity,
                                            isRealMainnet = true,
                                            selectedPool = selectedPool,
                                            onLog = { log -> consoleLogs.add(log) },
                                            onCompleted = { success ->
                                                executionSuccess = success
                                                isExecuting = false
                                                isFinished = true
                                            }
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = SentinelGold,
                                        contentColor = CosmicBackground
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Filled.Bolt, contentDescription = "Mainnet", tint = CosmicBackground)
                                        Text("🟢 RIDE FINANCIALS ON MAINNET", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = CosmicBackground)
                                    }
                                }

                                Button(
                                    onClick = {
                                        isExecuting = true
                                        consoleLogs.clear()
                                        repository.executeArbitrageOpportunity(
                                            opportunity = activeOpportunity,
                                            isRealMainnet = false,
                                            selectedPool = selectedPool,
                                            onLog = { log -> consoleLogs.add(log) },
                                            onCompleted = { success ->
                                                executionSuccess = success
                                                isExecuting = false
                                                isFinished = true
                                            }
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = CosmicCardInner,
                                        contentColor = CosmicTextPrimary
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, CosmicDivider)
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Filled.Science, contentDescription = "Simulated", tint = CosmicTextPrimary)
                                        Text("🧪 RUN SIMULATED SANDBOX", fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                                    }
                                }

                                TextButton(
                                    onClick = {
                                        repository.activeOpportunityPrompt.value = null
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Ignore & Dismiss Opportunity", color = CosmicTextSecondary, fontSize = 12.sp)
                                }
                            }
                        } else {
                            // Is executing or finished
                            if (isExecuting) {
                                Text(
                                    text = "EXECUTING ARBITRAGE BUNDLE...",
                                    color = SentinelGold,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                                LinearProgressIndicator(
                                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                                    color = SentinelGold,
                                    trackColor = CosmicCardInner
                                )
                            } else {
                                Text(
                                    text = if (executionSuccess) "⚡ BUNDLE EXECUTION SUCCESS" else "❌ TRANSACTION REVERTED",
                                    color = if (executionSuccess) SentinelEmerald else Color(0xFFEF5350),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            // Scrollable Console Terminal
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(CosmicBackground)
                                    .border(1.dp, CosmicDivider, RoundedCornerShape(10.dp))
                                    .padding(10.dp)
                            ) {
                                androidx.compose.foundation.lazy.LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(consoleLogs.size) { index ->
                                        val log = consoleLogs[index]
                                        Text(
                                            text = log,
                                            color = if (log.startsWith("✅")) SentinelEmerald else if (log.startsWith("❌")) Color(0xFFEF5350) else CosmicTextPrimary,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 11.sp,
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            if (isFinished) {
                                Button(
                                    onClick = {
                                        repository.activeOpportunityPrompt.value = null
                                    },
                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (executionSuccess) SentinelEmeraldDim else CosmicCardInner,
                                        contentColor = if (executionSuccess) SentinelEmerald else CosmicTextPrimary
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, if (executionSuccess) SentinelEmerald else CosmicDivider)
                                ) {
                                    Text("DISMISS PROTOCOL SCREEN", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
