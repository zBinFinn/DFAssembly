# DF Assembly
## by zBinFinn

### Usage:
1. Clone the repository
2. Put the code you want to compile into the code.dft file
3. Run the main function of the Main class
4. Output is in the console :)
5. Copy the commands one at a time from console
6. Use Codeclient or Millomod with /dfgive clipboard to get the templates!

### Example Syntax:
```
FUNCTION "AllSupportedValues";
SET_VARIABLE "x" (num[25], str[Test], txt[<green>Text], vec[0 5 2], loc[5 2 3 45 2], snd[1 2 Pling]);
SET_VARIABLE "x" (var[LINE line], var[LOCAL %default money], var[GAME game], var[SAVE save]);
SET_VARIABLE "x" (part[Ash "amount":1;"horizontal":0;"vertical":0]);

NEW

FUNCTION "Test";
SET_VARIABLE "ParseNumber" (var[GAME Test], str[3]);
SET_VARIABLE "x" (var[LINE product], var[GAME Test], num[2]);
REPEAT "Multiple" (var[LINE index], var[LINE product]);
OPEN_BRACKET_REPEAT;
PLAYER_ACTION "SendMessage" (var[LINE index]);
CLOSE_BRACKET_REPEAT;
PLAYER_ACTION "SendMessage" (txt[<blue>Vector: ], vec[2 3 5], txt[<newline><gray>String: ], str[Hi!], txt[<newline><red>Number: ], num[235]);

NEW

PLAYER_EVENT "Join";
CALL_FUNCTION "Test";
```
(NEW means it will create multiple /dfgive commands to seperate the things)

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
- Strings: str[This is a string]
- Texts: txt['\<green>This is a text]
- Numbers: num[2543]
- Vectors: vec[23 2 5]
- Variables: var[SCOPE variable name]
- Sounds: snd[Pitch Volume Sound] i.e.
          snd[1 2 Pling]
- Particles: part[Particle "data":1;"data2":2]
- Locations: loc[x y z pitch yaw] i.e. loc[1 5 3 45 5]
