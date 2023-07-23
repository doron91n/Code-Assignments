/******************************************
*Exercise name: ex6
******************************************/
#include "GADT.h"
#include <math.h>
#include <string.h>
#define Max 16
/***************************** function declaration **************************/
RESULT AllocateCheck(ELM memory);
ELM CreateString();
ELM CreatePoint();
void CpyString(ELM a, ELM b);
void CpyPoint(ELM a, ELM b);
int CmpStrings( ELM a, ELM b);
int CmpPoints( ELM a, ELM b);
void AddStringToString( ELM a, ELM b);
void AddPointToPoint( ELM a, ELM b);
void PrintString(ELM a);
void PrintPoint(ELM a);
ELM CreateNewString();
ELM CreateNewPoint();
void FreeString(ELM a);
void FreePoint(ELM a);
HEAD CreateFirstElement(HEAD *head,int option);
HEAD AddNewElement(HEAD* head,int option);
void SearchElement(HEAD *head,int option);
RESULT deleteElement(HEAD *head,int option);
HEAD AddElementToElement(HEAD *head,int option);
/***************************  struct Point ***********************************/
typedef struct
{
int x,y;
}Point;
/***************************** AllocateCheck **********************************
* function name:AllocateCheck
* input: ELM memory
* output: returns outOfMem if allocations failed
* function operation: checks allocation process success,if failed free memory
* and return outOfMem
* ****************************************************************************/
RESULT AllocateCheck(ELM memory)
{//start of AllocateCheck
if (memory == NULL)
{//start of if (checks allocation success)
    free(memory);
    return outOfMem;
}//end of if (checks allocation success)
return success;
}//end of AllocateCheck
/***************************** CreateString ***********************************
* function name:CreateString
* input: void
* output: returns new str
* function operation: allocate space for a new string
* ****************************************************************************/
ELM CreateString()
{//start of CreateString
/*********** variables declaration **************/
char *str;
/************************************************/
str = (char*)calloc(Max,sizeof(char));
AllocateCheck(str);
return (ELM)str;
}//end of CreateString
/***************************** CreatePoint ************************************
* function name:CreatePoint
* input: void
* output: returns new point
* function operation: allocate space for a new point
* ****************************************************************************/
ELM CreatePoint()
{//start of CreatePoint
/*********** variables declaration **************/
Point *newP;
/************************************************/
newP = (Point*)calloc(1,sizeof(Point));
AllocateCheck(newP);
return (ELM)newP;
}//end of CreatePoint
/***************************** CpyString **************************************
* function name:CpyString
* input: ELM a, ELM b
* output: void
* function operation: copying string b into string a
* ****************************************************************************/
void CpyString(ELM a, ELM b)
{//start of CpyString
char *str1 = (char*)a;
char *str2 = (char*)b;
strcpy(str1, str2);
}//end of CpyString
/***************************** CpyPoint ***************************************
* function name:CpyPoint
* input: ELM a, ELM b
* output: void
* function operation: copying point b into point a
* ****************************************************************************/
void CpyPoint(ELM a, ELM b)
{//start of CpyPoint
Point *p1 = (Point*)a;
Point *p2 = (Point*)b;
p1 -> x = p2 -> x;
p1 -> y = p2 -> y;
}//end of CpyPoint
/***************************** CmpStrings *************************************
* function name:CmpStrings
* input: ELM a, ELM b
* output: int cmp
* function operation: compare string a and string b returns the compare result
* 0 if equal | 1 if a > b | -1 if a < b
* ****************************************************************************/
int CmpStrings( ELM a, ELM b)
{//start of CmpStrings
/*********** variables declaration **************/
int cmp = 0,cmpResult = 0;
/************************************************/
cmpResult = strcmp(a, b);
if(cmpResult == 0)
    cmp = 0;
if(cmpResult > 0)
    cmp = 1;
if(cmpResult < 0)
    cmp = -1;
return cmp;
}//end of CmpStrings
/***************************** CmpPoints **************************************
* function name:CmpPoints
* input: ELM a, ELM b
* output: int cmp
* function operation: compare point a and point b by the distance of the x
* and y values returns the compare result:
* 0 if equal | 1 if d1(a) > d2(b) | -1 if d1(a) < d2(b)
* ****************************************************************************/
int CmpPoints( ELM a, ELM b)
{//start of CmpPoints
/*********** variables declaration **************/
int d1 = 0,d2 = 0,cmp = 9;
/************************************************/
Point *p1 = (Point*)a;
Point *p2 = (Point*)b;
d1 = abs(p1 -> x) + abs(p1 -> y);
d2 = abs(p2 -> x) + abs(p2 -> y);
//checks the x,y values are the same before checking distance
if((p1 -> x == p2 -> x)&&(p1 -> y == p2 -> y))
{
if(d1 == d2)
    cmp = 0;
}
if( d1 > d2)
    cmp = 1;
if( d1 < d2)
    cmp = -1;
return cmp;
}//end of CmpPoints
/***************************** AddStringToString ******************************
* function name:AddStringToString
* input: ELM a, ELM b
* output: void
* function operation: adds string b at the end of string a
* ****************************************************************************/
void AddStringToString( ELM a, ELM b)
{//start of AddStringToString
char* str1 = (char*)a;
char* str2 = (char*)b;
strcat(str1,str2);
}//end of AddStringToString
/***************************** AddPointToPoint ********************************
* function name:AddPointToPoint
* input: ELM a, ELM b
* output: void
* function operation: adds point b to point a
* ****************************************************************************/
void AddPointToPoint( ELM a, ELM b)
{//start of AddPointToPoint
Point *p1 = (Point*)a;
Point *p2 = (Point*)b;
p1 -> x +=  p2 -> x;
p1 -> y +=  p2 -> y;
}//end of AddPointToPoint
/***************************** PrintString ************************************
* function name:PrintString
* input: ELM a
* output: void
* function operation: prints input string
* ****************************************************************************/
void PrintString(ELM a)
{//start of PrintString
char *string = (char*)a;
printf("%s\n", string);
}//end of PrintString
/***************************** PrintPoint *************************************
* function name:PrintPoint
* input: ELM a
* output: void
* function operation: prints input point
* ****************************************************************************/
void PrintPoint(ELM a)
{//start of PrintPoint
Point *point = (Point*)a;
printf("%.3d|%.3d\n", point -> x, point -> y);
}//end of PrintPoint
/***************************** CreateNewString ********************************
* function name:CreateNewString
* input: void
* output: newString
* function operation: creates new String and scans user input into it
* ****************************************************************************/
ELM CreateNewString()
{//start of CreateNewString
/*********** variables declaration **************/
char *newString;
/************************************************/
newString = CreateString();
scanf("%s",newString);
return (char*)newString;
}//end of CreateNewString
/***************************** CreateNewPoint *********************************
* function name:CreateNewPoint
* input: void
* output: newPoint
* function operation: creates new point and scans user input into it
* ****************************************************************************/
ELM CreateNewPoint()
{//start of CreateNewPoint
/*********** variables declaration **************/
Point *newPoint;
int x=0,y=0;
/************************************************/
scanf(" (%d,%d)",&x,&y);
newPoint = CreatePoint();
newPoint -> x = x;
newPoint -> y = y;
return (Point*) newPoint;
}//end of CreateNewPoint
/***************************** FreeString *************************************
* function name:FreeString
* input: ELM a
* output: void
* function operation:  Frees string (elm a)
* ****************************************************************************/
void FreeString(ELM a)
{//start of FreeString
char *string = a;
free((char*)string);
}//end of FreeString
/***************************** FreePoint **************************************
* function name:FreePoint
* input: ELM a
* output: void
* function operation: Frees Point (elm a)
* ****************************************************************************/
void FreePoint(ELM a)
{//start of FreePoint
Point *point = a;
free((Point*)point);
}//end of FreePoint
/***************************** CreateFirstElement *****************************
* function name:CreateFirstElement
* input: HEAD *head,int option
* output: returns *head
* function operation: creates first element (point/string based on int option)
* and sends it to SLCREATE to create the list
* ****************************************************************************/
HEAD CreateFirstElement(HEAD *head,int option)
{//start of CreateFirstElement
if(option == 1)
/***************** createFirstPoint ***************/
{//start of if (createFirstPoint)
    Point *firstPoint = NULL;
    firstPoint = CreateNewPoint();
    *head = SLCreate( (Point*)firstPoint,&CreatePoint,&CpyPoint,&CmpPoints,
                     &FreePoint,&PrintPoint,(ELM)AddPointToPoint );
    FreePoint(firstPoint);
}//end of if (createFirstPoint)
else
/************* createFirstString ******************/
{//start of else (createFirstString)
    char *firstString = NULL;
    firstString = CreateNewString();
    *head = SLCreate( (char*)firstString,&CreateString,&CpyString,&CmpStrings,
                     &FreeString,&PrintString,(ELM)&AddStringToString );
    FreeString(firstString);
}//end of else (createFirstString)
return (HEAD) *head;
}//end of CreateFirstElement
/***************************** AddNewElement **********************************
* function name:AddNewElement
* input: HEAD *head,int option
* output: returns *head
* function operation: creates new element (point/string based on int option)
* and sends it to SLAddListElement to be added to the list
* ****************************************************************************/
HEAD AddNewElement(HEAD* head,int option)
{//start of AddNewElement
if(option == 1)
/***************** addNewPoint ***************/
{//start of if (addNewPoint)
    Point *newPoint = NULL;
    newPoint = CreateNewPoint();
    SLAddListElement(*head,newPoint);
    FreePoint(newPoint);
}//end of if (addNewPoint)
else
/************* addNewString ******************/
{//start of else (addNewString)
    char* *newString = NULL;
    newString = CreateNewString();
    SLAddListElement(*head,newString);
    FreeString(newString);
}//end of else (addNewString)
return (HEAD*)*head;
}//end of AddNewElement
/***************************** SearchElement **********************************
* function name:SearchElement
* input: HEAD *head,int option
* output: void
* function operation: creates new element (point/string based on int option)
* and sends it to SLFindElement to check if its in the list
* prints TRUE if its in the list else prints FALSE
* ****************************************************************************/
void SearchElement(HEAD *head,int option)
{//start of SearchElement
if(option == 1)
/************* searchPoint ******************/
{//start of if (searchPoint)
    /***** variables declaration *****/
    Point *searchPoint = NULL,*searchResult = NULL;
    /*********************************/
    searchPoint = CreateNewPoint();
    searchResult = SLFindElement(*head,searchPoint);
    //if(searchResult != NULL)
    FreePoint(searchPoint);
    if(searchResult != NULL)
        printf("TRUE\n");
    else
        printf("FALSE\n");
}//end of if (searchPoint)
else
/************* searchString ******************/
{//start of else (searchString)
    char* *searchString, *searchResult;
    searchString = CreateNewString();
    searchResult = SLFindElement(*head,searchString);
    FreeString(searchString);
    if(searchResult != NULL)
        printf("TRUE\n");
    else
        printf("FALSE\n");
}//end of else (searchString)
}//end of SearchElement
/***************************** deleteElement **********************************
* function name:deleteElement
* input: HEAD *head,int option
* output: RESULT(failure / success)
* function operation: creates new element (point/string based on int option)
* and sends it to SLRemoveElement to be deleted from the list if it exist in it
* returns RESULT failure if delete failed (not in list) else RESULT success
* ****************************************************************************/
RESULT DeleteElement(HEAD *head,int option)
{//start of deleteElement
RESULT deleteResult;
if(option == 1)
/************* deletePoint ******************/
{//start of if (deletePoint)
    Point *deletePoint = NULL;
    deletePoint = CreateNewPoint();
    deleteResult = SLRemoveElement(*head,deletePoint);
    FreePoint(deletePoint);
}//end of if (deletePoint)
else
/************* deleteString ******************/
{//start of else (deleteString)
    char* *deleteString = NULL;
    deleteString = CreateNewString();
    deleteResult = SLRemoveElement(*head,deleteString);
    FreeString(deleteString);
}//end of else (deleteString)
return deleteResult;
}//end of deleteElement
/***************************** AddElementToElement ****************************
* function name:AddElementToElement
* input: HEAD *head,int option
* output: *head
* function operation: creates 2 new element (point/string based on int option)
* and sends them to SLAddToElement if the first(left)element  exist in the list
* adds the second(right) element to it
* ****************************************************************************/
HEAD AddElementToElement(HEAD *head,int option)
{//start of AddElementToElement
if(option == 1)
/*************** AddingPointToPoint ***************/
{//start of if (AddPointToPoint)
    /*********** variables declaration **********/
    int x1 = 0,x2 = 0,y1 = 0,y2 = 0;
    Point *toPoint = NULL,*addPoint = NULL;
    /********************************************/
    toPoint = CreatePoint();
    addPoint = CreatePoint();
    scanf(" (%d,%d) (%d,%d)",&x1,&y1,&x2,&y2);
    toPoint -> x = x1;
    toPoint -> y = y1;
    addPoint -> x = x2;
    addPoint -> y = y2;
    SLAddToElement(*head, toPoint, addPoint);
    FreePoint(toPoint);
    FreePoint(addPoint);
}//end of if (AddPointToPoint)
else
/************ AddingStringToString ****************/
{//start of else (AddStringToString)
    /*********** variables declaration ***********/
    char *addString = NULL, *toString = NULL;
    /*********************************************/
    toString = CreateString();
    addString = CreateString();
    scanf(" %s %s",toString,addString);
    SLAddToElement(*head,toString,addString);
    FreeString(addString);
    FreeString(toString);
}//end of else (AddStringToString)
return (HEAD) *head;
}//end of AddElementToElement
/******************************** main ***************************************/
int main()
{
/*********** variables declaration **************/
char assignment,dummy ;
int end = 0,option = 0;
HEAD head ;
RESULT deleteResult;
/************************************************/
scanf(" %d",&option);
//dummy gets the \n
scanf("%c",&dummy);
CreateFirstElement(&head,option);
do
{//start of do
    scanf(" %c",&assignment);
    //dummy gets the \n
    scanf("%c",&dummy);
    switch(assignment)
    {//start of switch
    /******************************* case a **************************************/
    case 'a':
        //add new element to list
        AddNewElement(&head,option);
    break;
    /******************************* case s **************************************/
    case 's':
        //search element in list
        SearchElement(&head,option);
    break;
    /******************************* case d **************************************/
    case 'd':
        //delete element from list
        deleteResult = DeleteElement(&head,option);
        if(deleteResult == listIsEmpty)
        {
            free(head);
            end = 1;
        }
    break;
    /******************************* case t **************************************/
    case 't':
        //add new element to an existing element in list
         AddElementToElement(&head,option);
    break;
    /******************************* case p **************************************/
    case 'p':
        //print list
        PrintList(head);
    break;
    /******************************* case l **************************************/
    case 'l':
        //print list length
        PrintListLength(head);
    break;
    /******************************* case e **************************************/
    case 'e':
        //delete list (free memory and finish program)
        SLDestroy(head);
        free(head);
        end = 1;
    break;
    /******************************* default *************************************/
    default:
        printf(" Error: Illegal input\n");
    break;
    }//end of switch
}//end of do
while(end != 1);
return 0;
}
/*****************************************************************************/
