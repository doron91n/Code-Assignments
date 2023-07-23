/******************************************
*Exercise name: ex6
******************************************/
#ifndef GADT_H
#define GADT_H
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
//typedef declaration
typedef void* ELM;
typedef void* HEAD;
//we were allowed to add enums
typedef enum{ success, outOfMem, badArgs, failure, listIsEmpty} RESULT;
HEAD SLCreate
(//start of SLCreate
    ELM head_val,
    ELM(*create_elm)(),
    void(*cpy_elm)(ELM, ELM),
    int(*cmp_elm)( ELM, ELM),
    void(*free_elm)(ELM),
    void(*print_elm)( ELM),
    ELM(*add_elm_to_elm)(ELM, ELM)
);//end of SLCreate
void PrintListLength(HEAD head);
void PrintList(HEAD head);
void SLDestroy(HEAD head);
RESULT SLAddListElement(HEAD* head, ELM node);
RESULT SLRemoveElement(HEAD* head, ELM node);
ELM SLFindElement(HEAD head, ELM node);
void SLAddToElement(HEAD* head, ELM toEl, ELM addEl);
#endif
