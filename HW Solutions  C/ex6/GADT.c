/******************************************
*Exercise name: ex6
******************************************/
#include "GADT.h"
/************************** struct NODE (S_NODE) *****************************/
typedef struct NODE
{
ELM data;
struct NODE *next;
}S_NODE;
/************************* *struct S_HEAD ************************************/
struct
{
S_NODE *firstLink;
int listSize;
//head functions
ELM(*add_elm_to_elm)(ELM, ELM);
void(*print_elm)(ELM);
void(*free_elm)(ELM);
int(*cmp_elm)(ELM, ELM);
void(*cpy_elm)(ELM, ELM);
ELM(*create_elm)();
}typedef S_HEAD;
/********************************* SLCreate ***********************************
* function name:SLCreate
* input: ELM head_val, ELM(*create_elm)(), void(*cpy_elm)(ELM, ELM),
         int(*cmp_elm)( ELM, ELM), void(*free_elm)(ELM),void(*print_elm)( ELM),
         ELM(*add_elm_to_elm)(ELM, ELM).
* output: (HEAD)head
* function operation: creates head and first link
*(if allocation failed quits program) add node as first link data and put the
* first link at the top of the list(head),transfer all inputed functions into
* head struct so we can use them later on.
* ****************************************************************************/
HEAD SLCreate( ELM head_val, ELM(*create_elm)(), void(*cpy_elm)(ELM, ELM),
    int(*cmp_elm)( ELM, ELM), void(*free_elm)(ELM),void(*print_elm)( ELM),
    ELM(*add_elm_to_elm)(ELM, ELM) )
{//start of SLCreate
S_HEAD *head = (S_HEAD*)malloc(sizeof(S_HEAD));
S_NODE *firstLink = (S_NODE*)malloc(sizeof(S_NODE));
if ((firstLink == NULL)||(head == NULL))
{//start of if (checks allocation success)
    free(firstLink);
    free(head);
    return 0;
}//end of if (checks allocation success)
//add all inputed functions into the head struct for later use
head -> create_elm = create_elm;
head -> cpy_elm = cpy_elm;
head -> cmp_elm = cmp_elm;
head -> free_elm = free_elm;
head -> print_elm = print_elm;
head -> add_elm_to_elm = add_elm_to_elm;
//create the first link,add head_val as his data and put at the top of the list
ELM element1 = create_elm();
cpy_elm(element1,head_val);
firstLink -> data = element1;
firstLink -> next = NULL;
head -> firstLink = firstLink;
head -> listSize = 1;
return (HEAD)head;
}//end of SLCreate
/********************************* SLAddListElement ***************************
* function name:SLAddListElement
* input: HEAD head, ELM node
* output: RESULT (success/outOfMem)
* function operation: creates new link
* (returns RESULT outOfMem if allocation failed else RESULT success)
* and add node as his data,the new link is entered at its rightful place,
* its sorted based on the links data(smaller on top).
* note: the use of tempHead is to make the code more readable
* by not having to do casting each time we need to use head.
* ****************************************************************************/
RESULT SLAddListElement(HEAD* head, ELM node)
{//start of SLAddListElement
/*********** variables declaration **************/
S_HEAD *tempHead = (S_HEAD*)head;
S_NODE *oldFirstLink = tempHead -> firstLink;
int cmp = 0;
/************************************************/
// start - create newlink and add node as his data
S_NODE *newLink = (S_NODE*)malloc(sizeof(S_NODE));
if (newLink == NULL)
{//start of if (checks allocation success)
    free(newLink);
    return outOfMem;
}//end of if (checks allocation success)
ELM element = tempHead -> create_elm();
tempHead -> cpy_elm(element, node);
newLink -> data = element;
newLink -> next = NULL;
// end - create newlink and add node as his data
if ( (tempHead -> cmp_elm(oldFirstLink -> data,newLink -> data)) == 1 )
{//start of if (checks if the FirstLink(oldFirstLink)is bigger then the newlink)
    //newLink point to firstLink as newLink (next)
    newLink -> next = oldFirstLink;
    //add newLink at the top of the list
    tempHead -> firstLink = newLink;
}//end of if (checks if the FirstLink(oldFirstLink)is bigger then the newlink)
else
{//start of else(Locate the node before the point of insertion)
    S_NODE *current = oldFirstLink;
    while(current -> next != NULL)
    {//start of while(finds the place newlink needs to be at)
        cmp = tempHead -> cmp_elm((current-> next)-> data ,newLink -> data);
        if(cmp == -1)
        {//start of while(Locate the node before the point of insertion)
            current = current -> next;
        }//end of while(Locate the node before the point of insertion)
        else
            break;
    }//end of while(finds the place newlink needs to be at)
    //add new link at his correct place
    newLink -> next = current -> next;
    current -> next = newLink;
}//end of else
tempHead -> listSize++;
return success;
}//end of SLAddListElement
/********************************* SLFindElement ******************************
* function name:SLFindElement
* input: HEAD head, ELM node
* output: (S_NODE*)currentLink
* function operation: checks if node is in the list
* if node was found returns the node link else returns empty node link
* note: the use of tempHead is to make the code more readable
* by not having to do casting each time we need to use head.
* ****************************************************************************/
ELM SLFindElement(HEAD head, ELM node)
{//start of SLFindElement
/*********** variables declaration **************/
int compareResult = 0,found = 0;
S_HEAD *tempHead = (S_HEAD*) head;
S_NODE *currentLink = tempHead -> firstLink;
/************************************************/
while(currentLink != NULL)
{//start of while
    compareResult = tempHead -> cmp_elm(currentLink -> data,node);
    if(compareResult == 0)
    {//start of if (found the node)
        found = 1;
        break;
    }//end of if (found the node)
    //advance currentLink if not found yet
    currentLink = (S_NODE*)currentLink -> next;
}//end of while
if (found != 1)
    currentLink = NULL ;
return (S_NODE*)currentLink;
}//end of SLFindElement
/********************************* SLRemoveElement ****************************
* function name:SLRemoveElement
* input: HEAD *head, ELM node
* output: void
* function operation: checks if node is in the list (using SLFindElement func)
* if node was found deletes it from the list (frees the link and his data),and
* returns RESULT success else returns RESULT failure,if the removed node was the
* only node in the list (and now its empty)  returns RESULT listIsEmpty.
* note: the use of tempHead is to make the code more readable
* by not having to do casting each time we need to use head.
* ****************************************************************************/
RESULT SLRemoveElement(HEAD *head, ELM node)
{//start of SLRemoveElement
/*********** variables declaration **************/
int compareResult = 0;
S_NODE *prevLink = NULL;
S_HEAD *tempHead = (S_HEAD*) head;
S_NODE *currentLink = tempHead -> firstLink;
//checks if node is in the list
S_NODE *searchResult = SLFindElement(tempHead,node);
/************************************************/
if (searchResult == NULL)
    {//start of if 1 (node To Remove is not in the list)
        printf("FALSE\n");
        return failure;
    }//end of if 1 (node To Remove is not in the list)
for(currentLink = tempHead -> firstLink; currentLink != NULL;
    currentLink = currentLink -> next)
{//start of for (removes node from list)
    compareResult = tempHead -> cmp_elm(currentLink -> data,node);
    if((compareResult == 0)&&(prevLink == NULL))
    {//start of if 2 (the node we want to remove is the firstLink)
        tempHead -> firstLink = currentLink -> next;
        break;
    }//end of if 2 (the node we want to remove is the firstLink)
    else
    {//start of else(belongs to if 2- the node to remove is not the first link)
        if((compareResult == 0)&&(prevLink != NULL))
        {//start of if 3(found node to remove and connects after and prev links)
            prevLink -> next = currentLink -> next;
            break;
        }//end of if 3(found node to remove and connects after and prev links)
        //advance prevLink
        prevLink = currentLink;
    }//end of else(belongs to if 2- the node to remove is not the first link)
}//end of for (removes node from list)
//free currentLink and his data ,updates the list size
tempHead -> free_elm( ((S_NODE*)currentLink) -> data );
tempHead -> free_elm( (S_NODE*)currentLink );
tempHead -> listSize--;
//if the link that was deleted was the only one return ListIsEmpty and end program
if(tempHead -> listSize == 0)
    return listIsEmpty;
else
    return success;
}//end of SLRemoveElement
/********************************* SLAddToElement *****************************
* function name:SLDestroy
* input: HEAD *head, ELM toEl, ELM addEl
* output: void
* function operation: checks if toEl is in the list (using SLFindElement func)
* if toEl was found creates a new element that is the sum of toEl and addel
* and adds it to the list then deletes toEl from the list(element replaces toEl).
* note: the use of tempHead is to make the code more readable
* by not having to do casting each time we need to use head.
* ****************************************************************************/
void SLAddToElement(HEAD *head, ELM toEl, ELM addEl)
{//start of SLAddToElement
/*********** variables declaration **************/
S_HEAD *tempHead = (S_HEAD*) head;
//checks if toEl is in the list
S_NODE *searchResult = SLFindElement(tempHead,toEl);
/************************************************/
if (searchResult != NULL)
{//start of if (toEl was found -> adding addEl to toEl)
    // create new element that is the sum of toEl and addel
    ELM element = tempHead -> create_elm();
    tempHead -> cpy_elm(element,toEl);
    element = tempHead -> add_elm_to_elm(element, addEl);
    // add the new element to the list and delete toEl
    SLAddListElement(head,element);
    SLRemoveElement(head,toEl);
    tempHead -> free_elm( (S_NODE*)element );
}//end of if (toEl was found -> adding addEl to toEl)
}//end of SLAddToElement
/********************************* PrintList **********************************
* function name:PrintList
* input: HEAD head
* output: void
* function operation: prints the list
* note: the use of tempHead is to make the code more readable
* by not having to do casting each time we need to use head.
* ****************************************************************************/
void PrintList(HEAD head)
{//start of PrintList
/*********** variables declaration **************/
int i=0, linksNum = 0;
S_HEAD *tempHead = (S_HEAD*)head;
S_NODE *currentLink = tempHead -> firstLink;
/************************************************/
while (currentLink != NULL)
{//start of while
    for (i = 0; i < linksNum; i++)
    {//start of for (adds TAB between each data printed)
        printf("\t");
    }//end of for (adds TAB between each data printed)
    tempHead -> print_elm(currentLink -> data);
    linksNum++;
    currentLink = (S_NODE*) currentLink -> next;
}//end of while
}//end of PrintList
/********************************* PrintListLength ****************************
* function name:PrintListLength
* input: HEAD head
* output: void
* function operation: prints the list size (number of links in it)
* note: the use of tempHead is to make the code more readable
* by not having to do casting each time we need to use head.
* ****************************************************************************/
void PrintListLength(HEAD head)
{//start of PrintListLength
/*********** variables declaration **************/
S_HEAD *tempHead = (S_HEAD*)head;
/************************************************/
printf("%d\n",tempHead -> listSize);
}//end of PrintListLength
/********************************** SLDestroy *********************************
* function name:SLDestroy
* input: HEAD head
* output: void
* function operation: deletes all the list links by using SLRemoveElement func
* note: the use of tempHead is to make the code more readable
* by not having to do casting each time we need to use head.
* ****************************************************************************/
void SLDestroy(HEAD head)
{//start of SLDestroy
/*********** variables declaration **************/
S_HEAD *tempHead = (S_HEAD*) head;
S_NODE *currentLink = tempHead -> firstLink;
S_NODE *nextLink = NULL;
/************************************************/
while (currentLink != NULL)
{//start of while
    nextLink = currentLink -> next;
    SLRemoveElement(head, currentLink -> data);
    currentLink = (S_NODE*)nextLink;
}//end of while
}//end of SLDestroy
