/******************************************
*Exercise name: ex5
******************************************/
#include "Mission_one_To_Four.h"
/********************************************************************************************** start of mission 1 ***********************************************************************************/
/****************************** function charSwap *********************************
* function name: charSwap
* input: char *x, char *y
* output: void
* operation function: gets two char variables and swaps between them
* ****************************************************************************/
void charSwap(char *x, char *y)
{//start of charSwap(char *x, char *y)
char temp = *x ;
*x = *y;
*y =  temp;
}// end of charSwap(char *x, char *y)
/****************************** function letterCheck **************************
* function name: letterCheck
* input: char *str
* output: return 1 if all letters are (a-z|A-Z) else returns 0;
* operation function:gets the string and checks if the letters are valid (a-z|A-Z)
* ****************************************************************************/
int letterCheck(char *str)
{//start of letterCheck(char *str)
/***************** variable declartion ******************/
int checkValue = 4,i=0,len=0;
/********************************************************/
len=strlen(str);
for(i=0;i<len;i++)
{//start of for
    if ( ((str[i] >= 'A') && ('Z' >= str[i])) || ((str[i] >= 'a') && ('z' >= str[i])) )
        checkValue=1;
    else
    {//start of else
        checkValue=0;
        break;
    }//end of else
}// end of for
return checkValue;
}//end of letterCheck(char *str)
/****************************** function SortString ***************************
* function name: SortString
* input: char *str
* output:reurns 1 if str string needed sorting and 0 if its sorted already
* operation gets a string and sorts its letters by alpabetic order
* (lowercase comes before uppercase) and prints the sorted string
******************************************************************************/
int SortString(char* str)
{//start of SortString(char* str)
/***************** variable declartion ******************/
int i=0,j=0,sorted=0;
char first,second,len=0;
/********************************************************/
len=strlen(str);
for(i=0;i<len-1;i++)
{//start of for 1(runs the sorting of the string)
    for(j=0;j<len-1;j++)
    {//start of for 2
        //if the letter is lowercase deduce 200 so when we
        //sort the lowercase will come before the uppercase
        if ( (str[j] >= 'A') && ('Z' >= str[j]) )
        first = (int)str[j];
        else
        first = (int)str[j]-200;
        if ( (str[j+1] >= 'A') && ('Z' >= str[j+1]) )
        second = (int)str[j+1];
        else
        second = (int)str[j+1]-200;
        //preforms the sort between first and second letters
        if( ((int)first > (int)second) && ((int)first != (int)second) )
        {//start of if
            charSwap(&str[j],&str[j+1]);
            sorted = 1;
        }//end of if
    }//end of for 2
}//end of for 1(runs the sorting of the string)
//checks if the user input string was sorted
if (sorted==0)
    printf("The string is already sorted\n");
else
    printf("The string after sorting is: %s\n",str);
return sorted;
}
/****************************** function main1 ***************************
* function name: main1
* input: void
* output:void
* operation the main function of mission1 gets user input and sends to the needed
* functions in order to sort the input alphabeticly
******************************************************************************/
int main1()
{//start of main1
/***************** variable declartion ******************/
int stringLength = 0,letterCheckValue=4;
char *string = 0,dummy;
/********************************************************/
printf("Please enter how many chars to allocate:\n");
scanf("%d",&stringLength);
//dummy gets the "\n"
scanf("%c",&dummy);
//allocate the needed space (user entered length + 2 for \0)
string = (char*)calloc((stringLength+1),sizeof(char));
    if (string == 0)
    {//start of if (checks allocation success)
        printf("Error: Cannot allocate Memory\n");
        return 1;
    }//end of if (checks allocation success)
printf("Allocated %d chars\n",stringLength);
printf("Please enter string to be sorted:\n");
//gets the user input string
scanf("%s",string);
//sends the string and checks if its letters are valid (a-z|A-Z)
letterCheckValue=(int)letterCheck(string);
if (letterCheckValue!=0)
        SortString(string);
else
    {
        printf("Error: Illegal input\n");
    }
free(string);
return 0;
}//end of main1
/********************************************************************************************** start of mission 2 ***********************************************************************************/
/****************************** function SubGenerator *************************
* function name: SubGenerator
* input: char *str, char *sub
* output: void
* operation function: gets a string(str) and  substring(sub) and checks
* if str is a concatenation of sub (counts and returns the number of concatenation)
* and if no concatenation found returns 0
* ****************************************************************************/
int SubGenerator(char *str, char *sub)
{//start of SubGenerator
/***************** variable declartion ******************/
int count=0,compare=6,i=0,j=0,subLen=0,stringLen=0;
/********************************************************/
subLen=strlen(sub);
stringLen=strlen(str);
if( subLen+1 == stringLen+1 )
{//start of if 1 (if the sub and string are the same size compare them)
    compare = strcmp(sub,str);
    if(compare==0)
            count=1;
}//end of if 1 (if the sub and string are the same size compare them)
else
{//start of else 1 (for if 1)
    while(str[i] != '\0')
    {//start of while 1(runs on each letter in str untill the end)
        j=0;
        while(sub[j] != '\0')
        {//start of while 2(runs on each letter in sub untill the end)
            if (sub[j] == str[i])
            {//start of if 3
            j++;
            i++;
            }//end of if 3
            else
                return 0;
        }//end of while 2(runs on each letter in sub untill the end)
        count++;
    }//end of while 1(runs on each letter in str untill the end)
}//end of else 1 (for if 1)
return count;
}//end of SubGenerator
/****************************** function main2 ***************************
* function name: main2
* input: void
* output:void
* operation the main function of mission2 gets user input and sends to the needed
* functions in order to check concatenation between 2 strings
******************************************************************************/
int main2()
{//start of main2
/***************** variable declartion ******************/
int firstLen=0,secondLen=0,returnVal=0,mod=9;
char dummy,*firstString=0,*secondString=0;
/********************************************************/
printf("Please enter how many chars to allocate to the two strings:\n");
scanf("%d %d",&firstLen,&secondLen);
//dummy gets the "\n"
scanf("%c",&dummy);
//checks if the input is valid (concatenation is possible)
if(firstLen >= secondLen)
    {//start of if 1
    //allocate the needed space (user entered length )
    secondString = (char*)calloc(secondLen+1,sizeof(char));
    if (secondString == 0)
    {//start of if (checks allocation success)
        printf("Error: Cannot allocate Memory\n");
        return 1;
    }//end of if (checks allocation success)
    //allocate the needed space (user entered length )
    firstString = (char*)calloc(firstLen+1,sizeof(char));
    if (firstString == 0)
    {//start of if (checks allocation success)
        printf("Error: Cannot allocate Memory\n");
        return 1;
    }//end of if (checks allocation success)
    printf("Allocated %d chars and %d chars\n",firstLen,secondLen);
    printf("Please enter two arrays of chars:\n");
    scanf(" %s %s",firstString,secondString);
    returnVal = SubGenerator(firstString,secondString);
    if(returnVal == 0)
        printf("No concatenation found\n");
    else
        printf("%s is a concatenation of %s %d times\n",firstString,secondString,returnVal);
    }//end of if 1
else
    printf("No concatenation found\n");
free(secondString);
free(firstString);
return 0;
}//end of main2
/********************************************************************************************** start of mission 3 ***********************************************************************************/
/****************************** function ShortestStr *************************
* function name: ShortestStr
* input: char *str
* output:int prefixLen (returns the length of the shortest prefix)
* operation function: gets a string(str) and looks for the shortest prefix
* that str is a concatenation of (counts and returns the length of the prefix)
* ****************************************************************************/
int ShortestStr(char *str)
{//start of ShortestStr
/***************** variable declartion ******************/
int returnVal=0,j=0,i=0,prefixLen=0,stringLen=0;
char *prefix=0;
/********************************************************/
//allocate the needed space (same as str + 1 for \0)
stringLen=strlen(str);
prefix = (char*)calloc((stringLen+1),sizeof(char));
if (prefix == 0)
{//start of if (checks allocation success)
    printf("Error: Cannot allocate Memory\n");
    return 1;
}//end of if (checks allocation success)
for(i=0;i<stringLen+1;i++)
{//start of for
    //for each letter in str(i) add to prefix(i) and send to SubGenerator
    prefix[i]=str[i];
    returnVal=SubGenerator(str,prefix);
    //as long as there are no concatenation add the next letter to prefix
    if (returnVal == 0)
        prefix[i+1]=str[i+1];
    else
        break;
}//end of for
prefixLen=strlen(prefix);
printf("The shortest prefix building the string is: %s of length %d\n",prefix,prefixLen);
free(prefix);
return prefixLen;
}//end of ShortestStr
/****************************** function main3 ***************************
* function name: main3
* input: void
* output:void
* operation the main function of mission2 gets user input and sends to the needed
* functions in order to check concatenation between 2 strings
******************************************************************************/
int main3()
{//start of main3
/***************** variable declartion ******************/
int stringLen=0;
char dummy,*string=0;
/********************************************************/
printf("Please enter how many chars to allocate:\n");
scanf("%d",&stringLen);
//dummy gets the "\n"
scanf("%c",&dummy);
//allocate the needed space (user entered length + 1 for \0)
string = (char*)calloc((stringLen+1),sizeof(char));
if (string == 0)
{//start of if (checks allocation success)
    printf("Error: Cannot allocate Memory\n");
    return 1;
}//end of if (checks allocation success)
printf("Allocated %d chars\n",stringLen);
printf("Please enter your string:\n");
scanf("%s",string);
ShortestStr(string);
free(string);
return 0;
}//end of main3
/********************************************************************************************** start of mission 1 ***********************************************************************************/
/****************************** function charReverse **************************
* function name: charReverse
* input: char *str, int strStart,int strEnd
* output: void
* operation function:reverse each char in a given string from start
* of the string to its end, by getting the starting point and end point of the
* string we wanna reverse, (string can be a single word as well as a sentence).
* ****************************************************************************/
void charReverse(char *str, int strStart,int strEnd)
{//start of charReverse
while(strEnd > strStart)
{//start of while
    char temp = str[strStart] ;
    str[strStart] = str[strEnd];
    str[strEnd] =  temp;
    strEnd--;
    strStart++;
}//end of while
}// end of charReverse
/********************* function wordReverse ***********************************
* function name: wordReverse
* input: char *str,int length
* output: void
* operation function:reverse each word in a given string from start
* of the string to its end, by getting the length of the string we wanna reverse
* we can reverse only the desired part of a string.
* first we reverse the order of words and the we reverse each word to it orignal form.
* ****************************************************************************/
void wordReverse(char *str,int length)
{//start of wordReverse
/***************** variable declartion ******************/
int wordStart = 0,i = 0;
/********************************************************/
//first reverse all chars in the string
charReverse(str,wordStart,length-1);
while(length > i)
{//start of while 1(reverse each word in the string)
    wordStart = i;
    //advences i until the end of the word
    while( (str[i] != ' ') && (str[i] != '\0') )
    {//start of while 2
        i++;
    }//end of while 2
    //sends each word to charReverse
    charReverse(str,wordStart,i-1);
    //skips space
    while(str[i]==' ')
    {//start of while 3
        i++;
    }//end of while 3
}//end of while 1(reverse each word in the string)
}// end of wordReverse
/*********************** function ReverseNumWords *****************************
* function name: ReverseNumWords
* input: char *str, int n
* output: void
* operation function:reverse the order of the first n(number) words in a given
* string by using wordReverse function and print the reversed string.
* ****************************************************************************/
int ReverseNumWords(char *str, int n)
{//start of ReverseNumWords
/***************** variable declartion ******************/
int charCount=0,j=0,spaceCount=0,sentenceLen=0;
/********************************************************/
sentenceLen=strlen(str);
for(j = 0;j < sentenceLen;j++)
{//start of for(counts the number of chars to swap)
    //if 1
    if (str[j] == ' ')
        spaceCount++;
    //if 2
    if(spaceCount < n)
        charCount++;
}//end of for(counts the number of chars to swap)
//checks if the reveres is possible(string have the amount of words to replace)
if(spaceCount+1 < n)
{//start of if 3
    printf("Error: Illegal input\n");
    return 0;
}//end of if 3
else
{//start of else(if 3)
    wordReverse(str,charCount);
    printf("The string after reverse is: %s\n",str);
}//end of else(if 3)
}//end of ReverseNumWords
/****************************** function main4 ***************************
* function name: main2
* input: void
* output:void
* operation the main function of mission2 gets user input and sends to the needed
* functions in order to check concatenation between 2 strings
******************************************************************************/
int main4()
{//start of main4
/***************** variable declartion ******************/
int sentenceLen=0,wordsToRev=0,len=0;
char dummy,*sentence=0,c;
/********************************************************/
// part of it was taken from https://www.youtube.com/watch?v=ql9CCdiP-_A
printf("Please enter how many chars to allocate:\n");
scanf("%d",&sentenceLen);
//dummy gets the "\n"
scanf("%c",&dummy);
//allocate the needed space (user entered length + 1 for \0)
sentence = (char*)calloc((sentenceLen+2),sizeof(char));
if (sentence == 0)
    {//start of if (checks allocation success)
        printf("Error: Cannot allocate Memory\n");
        return 1;
    }//end of if (checks allocation success)
printf("Allocated %d chars\n",sentenceLen);
printf("Please enter your string:\n");
fgets(sentence,sentenceLen+2,stdin);
len=strlen(sentence);
//replace the '\n' with '\0' at the end of string
if(sentence[len-1] == '\n')
    sentence[len-1] ='\0';
printf("Please enter how many words to reverse:\n");
scanf(" %d",&wordsToRev);
//dummy gets the "\n"
scanf("%c",&dummy);
ReverseNumWords(sentence, wordsToRev);
free(sentence);
}//end of main4

