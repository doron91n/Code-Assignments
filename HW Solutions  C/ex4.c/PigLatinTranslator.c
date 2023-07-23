/******************************************
*Exercise name: ex4
******************************************/
#include "PigLatinTranslator.h"
/**************************************************************************************
* function name: SentenceScan()
* input: void
* output: void
* operation function:gets the sentence from user transforms all letters to lowercase
* and separates the sentence to single words
* *************************************************************************************/
void SentenceScan()
{//start of SentenceScan()
/*********** variables declaration **************/
const char s[4]=" \n\t";
char sentence[MAX_SENTENCE],answer,dummy;
int letter=0;
char* token, word[MAX_WORD];
/************************************************/
    do
    {//start of do(as long as the answer is y)
        printf("Please enter a sentence in English:\n");
        fgets(sentence,MAX_SENTENCE,stdin);//gets sentence
        //transforms all letters to lowercase
        for(letter=0;sentence[letter]!='\0'&&sentence[letter] != '\n';letter++)
        {
            sentence[letter]=tolower(sentence[letter]);
        }
        token=strtok(sentence,s);//separates the sentence to single words
        while(token!=NULL)
            {//start of while
                strcpy(word,token);
                PrintWord(word);
                token =strtok(NULL,s);
            }//end of while
        printf("\n");
        printf("Do you want to translate another sentence?(y/n)\n");
        scanf(" %c",&answer);
        scanf("%c", &dummy);
	}
    while(answer=='y');
}//end of SentenceScan()
/**************************************************************************************
* function name: PrintWord()
* input: char word[MAX_WORD]
* output: void
* operation function:gets the words and checks for the vowel locations and alters the word based on the rules
* and prints the resultif it starts with (aeiou)else sends it to PrintTransferedWord(does all needed alterations and prints the altered words)
* *************************************************************************************/
void PrintWord(char word[MAX_WORD])
{
    int vowelIndex =1;
    if(VowelCheck(word[0]))
    {
        strcat(word,"way");
        printf("%s ",word);
    }
    else
    {
    //gets the vowel position in the word
    while((VowelCheck(word[vowelIndex])==0)&&((word[vowelIndex])!='y')&&(word[vowelIndex]!='\0'))
            vowelIndex++;
        PrintTransferedWord(word,vowelIndex);
    }
}
/**************************************************************************************
* function name: PrintTransferedWord
* input: char newWord[MAX_WORD],int vowelIndex
* output: void
* operation function:gets the words and checks for the vowel locations and alters the word based on the rules
* and prints the result(does all needed alterations and prints the altered words)
* *************************************************************************************/
void PrintTransferedWord(char newWord[MAX_WORD],int vowelIndex)
{
int i,j;
char vowelArray[MAX_WORD];
    for(i=0,j=vowelIndex;newWord[j]!='\0';j++,i++)//transfer the letter in the j place to the i place (0)
        {
            vowelArray[i]=newWord[j];
        }
for(j=0,i;j<vowelIndex;j++,i++)//transfer the letter in the j(0) place to the i place
{
    vowelArray[i]=newWord[j];
}
            vowelArray[i]='\0' ;
            strcat(vowelArray,"ay");
            printf("%s ",vowelArray);
}
/**************************************************************************************
* function name: VowelCheck
* input: char letter
* output: return 1 if the letter is (a\e\i\o\u) else 0;
* operation function:gets the words and checks if the letter is (a\e\i\o\u)
* *************************************************************************************/
int VowelCheck(char letter)
{
    if((letter=='a')||(letter=='e')||(letter=='i')||(letter == 'o')||(letter == 'u'))
    {
        return 1;
    }
    return 0;
}
/**************************************************************************/
