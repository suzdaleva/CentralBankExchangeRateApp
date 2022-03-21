package com.manicpixie.cfttest.presentation.currencies_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.manicpixie.cfttest.R
import com.manicpixie.cfttest.common.Constants
import com.manicpixie.cfttest.domain.model.Currency
import com.manicpixie.cfttest.presentation.utils.dpToSp
import com.manicpixie.cfttest.ui.theme.*


@Composable
fun CurrencyItem(
    modifier: Modifier = Modifier,
    currency: Currency,
) {

    val change = currency.previous - currency.value
    val arrow = if(change>=0f) painterResource(id = R.drawable.percent_up_arrow) else painterResource(id = R.drawable.percent_down_arrow)
    Card(modifier = modifier
        .fillMaxWidth()
        .height(114.dp)
        .padding(horizontal = 20.dp, vertical = 5.dp),
    elevation = 6.dp) {
        Row(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(15.dp))
            Box(modifier = Modifier
                .size(55.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(MaterialTheme.colors.background), contentAlignment = Alignment.Center){
                Text(
                    text = currency.charCode,
                    style = TextStyle(
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFBD00FF),
                        fontSize = dpToSp(dp = 15.dp),
                        letterSpacing = 0.04.em
                    )
                )
            }
            Spacer(modifier = Modifier.width(18.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        softWrap = true,
                        modifier= Modifier.width(147.dp),
                        text = currency.name,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.h4,
                        fontSize = dpToSp(dp = 14.dp),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = Constants.currenciesNames[currency.charCode]!![1],
                        style = MaterialTheme.typography.h6,
                        color = DarkGray,
                        fontSize = dpToSp(dp = 11.dp),
                    )
                }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically){
                        Text(
                            text = "%.3f".format(currency.value/currency.nominal).replace(',', '.'),
                            style = MaterialTheme.typography.h3,
                            color = MaterialTheme.colors.onSurface,
                            fontSize = dpToSp(dp = 15.dp)
                        )
                            Spacer(modifier = Modifier.width(3.dp))
                        Icon(modifier = Modifier
                            .size(12.dp)
                            .padding(top = 1.dp),painter = painterResource(id = R.drawable.rouble),
                            contentDescription = "rouble",
                        tint = MaterialTheme.colors.onSurface)}
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(modifier = Modifier.size(12.dp),painter = arrow,
                                contentDescription = "arrow")
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${change.toString().substring(0,5)}",
                                fontSize = dpToSp(dp = 15.dp),
                                style = MaterialTheme.typography.h3,
                                color = DarkGray
                            )
                        }
                    }
        }

    }
}