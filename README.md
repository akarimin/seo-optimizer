seo-optimizer application scores the keyword provided as an input based on return values of http://completion.amazon.com/search/ character by character.

The final score is calculated in 3 steps:

1- Iteration number the keyword appears.          --> LoopScore=1~10
2- Amazon index number the whole keyword appears. --> RelevanceScore=10~100  
3- Summation of all relevanceScores for a specific keyword, multiplied by its corresponding LoopScore, divided by 550. 
