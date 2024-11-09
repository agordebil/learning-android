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
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
                var result = remember{
                    mutableDoubleStateOf(0.0)
                }


                    Column() {
                        Row() {
                           // Text(text = result.toString(), fontSize = 30.sp, modifier= Modifier.padding(30.dp))
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
                                EqualToButton(charslist,'=',result) //this button will be different, it will execute the generated function
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
fun EqualToButton(chars:MutableList<Char>,num:Char, equalsDeez:MutableState<Double>){
    Button(onClick = {
        var txt = chars.toList()
        val sym = arrayOf('+','-','*','/','=','<')
        //reduce array into symbols, find its length, its length +1 is how many numbers you
        //have. hashmap symbols into array and push numbers into a list as string
        //make them int with toInt and execute the operation.
        //the result will be displayed as a secondary row - under the current charslist

        println(equalsDeez)
        var mapSym = mutableMapOf<Int, Char>()
        var txtEdited = txt.toMutableList()
        println(txtEdited.toString())
        for(i in txt.indices){
            if(sym.indexOf(txt[i])!=-1){
                mapSym[i] = txt[i]
                txtEdited[i] = '_'
            }
        }
        var txtStr =  txtEdited.joinToString(separator = "")
        var unres =  txtStr.split('_') //these are the numbers
       // var variableCount:Int = mapSym.size+1

        for(key in mapSym.keys){
            if (mapSym[key] != '/' && mapSym[key] != '*'){
                equalsDeez.value = unres[0].toDouble()
                var index = 1
                for(value in mapSym.values){
                    when(value){
                        '-' -> equalsDeez.value = equalsDeez.value - unres[index].toDouble()
                        '+' -> equalsDeez.value = equalsDeez.value + unres[index].toDouble()
                //      '*' -> equalsDeez.value = equalsDeez.value * unres[index].toDouble()
                //      '/' -> equalsDeez.value = equalsDeez.value / unres[index].toDouble()
                    }
                    index++
                }
            }
        }

        //Check if we have consequent * or /
        var conseq:Int = 0
        var former: Char? = mapSym[mapSym.keys.elementAt(0)]
        for(key in mapSym.keys){
            if (mapSym[key] == '/' || mapSym[key] == '*'){
                if(mapSym[key] == former){
                    conseq++
                }
            }
            former = mapSym[key]
        }

        //doesnt work
        //non-conseq * & / included with operation prio
        var newNumbers: MutableList<Int> = mutableListOf()
//        var newDebug: MutableList<Int> = mutableListOf()

        if (conseq ==1){ //this means we have * or / but not consequent
            if(mapSym.values.size + 1 == unres.size){
                for(i in mapSym.values.indices){
                    if(mapSym.values.elementAt(i) == '*'){
                        var temp:Int = unres[i].toInt() * unres[i+1].toInt()
                        newNumbers.add(temp)
                    }else if(mapSym.values.elementAt(i) == '/'){
                        var temp:Int = unres[i].toInt() / unres[i+1].toInt()
                        newNumbers.add(temp)
                    }else{
                        newNumbers.add(unres[i].toInt())
                    }
                }
            }
            if(newNumbers.size>0){
                equalsDeez.value = newNumbers[0].toDouble()
            }
            var ind = 1
            for (value in mapSym.values){
                when(value){
                    '-' -> equalsDeez.value = equalsDeez.value - newNumbers[ind].toDouble()
                    '+' -> equalsDeez.value = equalsDeez.value + newNumbers[ind].toDouble()
                }
                ind++
            }
        }
//    println(equalsDeez)
        println(unres)
        println(newNumbers.toString())
        println(mapSym.values)

    }
    ){
        Text(text = "$num", color = Color.White, fontSize = 16.sp)
    }
}




@Composable
fun BackspaceButton(chars:MutableList<Char>,num:Char){
    Button(onClick = {
        if(chars.size >0){
            chars.removeAt(chars.lastIndex)
        }
    }
){
    Text(text = "$num", color = Color.White, fontSize = 16.sp)
}
}


@Composable
fun CalcButton(chars:MutableList<Char>,num:Char){
    Button(onClick = {
        var sym = arrayOf('+','-','*','/','=','<')
        if(chars.size == 0 && (num == '0' || sym.indexOf(num)!=-1) ) {
            return@Button
        }
        if((chars.size != 0 && sym.indexOf(chars.last()) != -1) && (num == '0' || sym.indexOf(num)!=-1)){
            return@Button
        }
        chars.add(num)
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