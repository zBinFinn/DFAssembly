# DF Assembly
## by zBinFinn

### Example Syntax:
```
FUNCTION "Test";
SET_VARIABLE "ParseNumber" (GAME Test, "3");
SET_VARIABLE "x" (product, GAME Test, 2);
REPEAT "Multiple" (index, product);
OPEN_BRACKET_REPEAT;
PLAYER_ACTION "SendMessage" (index);
CLOSE_BRACKET_REPEAT;
PLAYER_ACTION "SendMessage" ('<blue>Vector: ', <2 3 5>, '<gray>String: ', "Hi!", '<red>Number: ', 235);

NEW

PLAYER_EVENT "Join";
CALL_FUNCTION "Test";
```
(NEW means it will create 2 /dfgive commands to seperate the things)

### This project currently supports:
Block Types:
- PLAYER EVENT
- FUNCTION
- PROCESS

- CALL_FUNCTION
- PLAYER_ACTION
- SET_VARIABLE
- CONTROL

- IF_PLAYER
- OPEN_BRACKET
- CLOSE_BRACKET

- REPEAT
- OPEN_BRACKET_REPEAT
- CLOSE_BRACKET_REPEAT

Parameters:
- Strings: "This is a String"
- Texts: '\<green>This is a Text'
- Numbers: 2543
- Vectors: <23 2 5>
- Variables: 
  - GAME Variable
  - LOCAL Variable
  - SAVE Variable
  - Variable (<-- Automatically LINE) 
