# comp3021lab

Yuen Ho Ting Nicholas 20694986

# Code Interview 1 for Lab  

# Task 1: Difficulties for Lab 3
    
Difficulty:  
Converting keyword into boolean conditions in class Folder searchNotes function.

Solution:  
As the search is case-insensitive, we first convert all comparing targets into lower case letters.
Then, we split the input string, which is the keyword, by the deliminator " ". 
Afterwards, we check every note in notes of the Folder class, we only check for the title for ImageNote 
and checks both the title and contents for TextNotes.  
To check whether the string contains the keyword condition, I have defined a helper function:
private boolean contains(String content, String[] tokens, int index);  
This is a recursive function where only the variable index would be updated.
In the base case, it returns true after completed the searching for the whole target string.
Otherwise, it checks whether the next keyword is "or" operation.
If this is the case, it returns the searching result on whether the target string 
contains either one of the current keyword or the keyword after the "or" operation 
and continues the searching after the second keyword in the "or" condition via calling  
return (content.contains(tokens[index]) || content.contains(tokens[index+2]) )&& contains(content, tokens, index+3);
Or else, it returns the searching result of the current keyword and continues the searching after this keyword by calling    
return content.contains(tokens[index]) && contains(content, tokens, index+1);

# Task 2:

First iterate through each word in the text(string) and add them to a Map structure, 
where the Map should store the word as the key and word count as the corresponding value.
If the word already exists in the map, it will increase its count.  
Then we sort the values in the map data structure according to descending order 
and simply just return the first 3 words. 
As the sorting algorithm runs in O(nlogn), this algorithm runs in O(nlogn) in the worst case.  
To make it more efficient, we only need to replace the second step by running 
the selection algorithm for the first, second, third maximum values for the count.
As the iteration in first step only takes O(n) and each selection algorithm takes O(n), 
the world case for the algorithm only runs in O(n).