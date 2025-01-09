package com.artiuil.lab.cleverdictionary.ui.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Pixel 5",
    device = "spec:width=1080dp,height=2400dp,dpi=480",
    showSystemUi = true
)
@Preview(
    name = "Samsung Galaxy S21 Ultra",
    device = "spec:width=1440dp,height=3200dp,dpi=515",
    showSystemUi = true
)
@Preview(
    name = "Foldable",
    device = "spec:width=673dp,height=841dp",
    showSystemUi = true
)
@Preview(
    name = "Phone",
    device = "spec:width=411dp,height=891dp",
    showSystemUi = true
)
@Preview(
    name = "Tablet",
    device = "spec:width=1280dp,height=800dp,dpi=240",
    showSystemUi = true
)
annotation class MultiDevicePreview