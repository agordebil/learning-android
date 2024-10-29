package com.example.learningcompose

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learningcompose.ui.theme.LearningComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearningComposeTheme {
                var charslist = remember {
                    mutableStateListOf<Char>()
                }
                var result by remember{
                    mutableStateOf(0)
                }


                    Column() {
                        Row() {
                            Text(text = charslist.joinToString(separator = ""), fontSize = 30.sp, modifier= Modifier.padding(30.dp))
                        } //display result
                        Row() {
                            Column() {
                                //first column,  7 4 1 and =
                                CalcButton(charslist,'7')
                                CalcButton(charslist, '4')
                                CalcButton(charslist, '1')
                                CalcButton(charslist, '-')
                            }
                            Column() {//second column, 8 5 2 0
                                CalcButton(charslist,'8')
                                CalcButton(charslist,'5')
                                CalcButton(charslist,'2')
                                CalcButton(charslist,'0')
                            }
                            Column() {
                                //third column 9 6 3 and ^
                                CalcButton(charslist,'9')
                                CalcButton(charslist,'6')
                                CalcButton(charslist,'3')
                                CalcButton(charslist,'+')
                            }
                            Column() {
                                //fourth column, + - * /
                                BackspaceButton(charslist,'<')
                                CalcButton(charslist,'=') //this button will be different, it will execute the generated function
                                CalcButton(charslist,'*')
                                CalcButton(charslist,'/')
                            }
                        }
                    }
            }
        }
    }


}

@Composable
fun EqualToButton(chars:MutableList<Char>,num:Char){
    Button(onClick = {
        var txt = chars.toList()
        var sym = arrayOf('+','-','*','/','=','<')
        //reduce array into symbols, find its length, its length +1 is how many numbers you
        //have. hashmap symbols into array and push numbers into a list as string
        //make them int with toInt and execute the operation.
        //the result will be displayed as a secondary row - under the current charslist
    }
    ){
        Text(text = "$num", color = Color.White, fontSize = 16.sp)
    }
}




@Composable
fun BackspaceButton(chars:MutableList<Char>,num:Char){
    Button(onClick = {
        chars.removeAt(chars.lastIndex)
        println(chars.joinToString(separator = ""))
    }
){
    Text(text = "$num", color = Color.White, fontSize = 16.sp)
}
}


@Composable
fun CalcButton(chars:MutableList<Char>,num:Char){
    Button(onClick = {
        var sym = arrayOf('+','-','*','/','=','<')
        if(chars.size == 0 && sym.indexOf(num)==-1) {
            chars.add(num)
        }
    }){
        Text(text = "$num", color = Color.White, fontSize = 16.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LearningComposeTheme {

    }
}