
## seo-optimizer ## 

The application scores the keywords provided by user as inputs based on return values of http://completion.amazon.com/search/ character by character.

The final score is calculated in 3 steps:

1. Iteration number the keyword appears. `LoopScore` is an `integer` in range of 1 to 10.
2. Amazon index number the whole keyword appears. `RelevanceScore` is an `integer` in range of 10 to 100.  
3. `TotalScore` is summation of all relevanceScores for a specific keyword, multiplied by its corresponding LoopScore, divided by 550 to be normalized in range of 0 to 100.

This could be a handy tool to optimize/figure out what keywords boost your website's SEO in any business context.
