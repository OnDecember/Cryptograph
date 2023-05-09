# Cryptograph
**Hello!** This is a cryptographer - a program that can encrypt/decrypt text with the Caesar cipher, as well as break the cipher in 2 ways (frequency and statistical analysis).
The program can be launched in 2 ways:
  1. Passing arguments at startup.
  2. Through the desktop application.

### **Keywords:**
1. `jarFile` - the path to the jar file
2. `command` - a command to launch the program in one of the modes:

`-e` - encryption,

`-d` - decryption,

`-bf` - break the cipher (brute force)

3. `filePath` - the path to the main file
4. `additionalFile` - the path to the additional file

## Features that are available for launch:

1. ### Encryption - encrypts the text with the given key.

**Template to launch:**
`java -jar jarFile command filePath key`

**Example:**
`java -jar c:/MyProject/target/myApp.jar -e folder/srcFile .txt 20`

**Result:** Encrypted file marked **[ENCRYPTED]**

2. ### Decryption - decrypts the text with the specified key.

**Template to launch:**
`java -jar jarFile command filePath key`

**Example:**
`java -jar c:/MyProject/target/myApp.jar -d folder/srcFile .txt 20`

**Result:** Encrypted file marked **[DECRYPTED]**

3. ### Brute force - breaks the cipher in two ways:

  1). _Frequency analysis_ - tries to find a key to
  encrypted text

  **Template to launch:**
  `java -jar jarFile command filePath`
  
  **Example:**
  `java -jar c:/MyProject/target/myApp.jar -bf folder/srcFile .txt`

  **The result:** an encrypted file in the name of which will be the key with which it was possible to crack the cipher.

  **Issues:** In some cases, it may not display if the text does not match the text validation pattern. For this, you can use the second method.

  2). _Statistical analysis_ - uses an additional file to analyze the percentage ratio of characters between two files, and selects a key based on the ratio.
  
  **Importantly!** The first file you need to specify is the file that needs to be hacked.

  **Template to launch:**
  `java -jar jarFile command filePath additionalFile`
  
  **Example:**
  `java -jar c:/MyProject/target/myApp.jar -bf folder/srcFile .txt folder/additionalFile.txt`

  **The result:** an encrypted file in the name of which will be the key with which it was possible to crack the cipher.

  **Problems:** if the text is too small, the key may be incorrectly selected due to low accuracy of calculations.

  **Recommendations:** if one of the methods did not work, try another.

  If you do not pass arguments, but only run the program, it will run as a desktop application.

In this application, you can choose one of the launch modes, as well as specify key if the command is -e or -d, or instead of key specify additionalFile if the command is -bf and you use statistical analysis. For frequency analysis, you do not need to specify anything in the key field.

### Additional Information:

1. The cipher uses English and Ukrainian languages, as well as symbols `[ . , « » " ' : ! ? ]` and a space. Other characters are not encoded.

2. If the text contains both languages, then the hack will work only for one language, the other language will not be encoded.

3. Frequency analysis works with a RegEx expression, and compares the decoded string with the expression.

4. In the statistical analysis, the Map interface is used for the convenience of determining the number of symbols and the percentage ratio.

5. The Swing library is used for the desktop application.
