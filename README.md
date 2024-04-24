<a href="https://www.code-intelligence.com/">
<img src="https://www.code-intelligence.com/hubfs/Logos/CI%20Logos/Logo_quer_white.png" alt="Code Intelligence logo" width="450px">
</a>

# Java Demo

This Java Demo is an example project to showcase the usage of white-box fuzz testing for developers and security experts.
It features examples for the usage as security issue detector as well as robustness issue detector.

The project contains multiple examples:
* [Simple Testing Example](src/test/java/com/demo/libraries/SimpleExampleTest.java):
  A simple example showcasing how minor the syntax differences between a fuzz test and unit test are.
* [Robustness Examples](src/test/java/com/demo/Controller/CarCategoryControllerTest.java):
  Multiple examples ranging from simple to more complex showcasing the testing of APIs for error 5xx.
* [Security Examples](src/test/java/com/demo/Controller/UserControllerTest.java):
  Multiple example showing how to use fuzz testing to check for security vulnerabilities in webservers.
* [Advanced Example](src/test/java/com/demo/Controller/CarControllerTest.java):
  An advanced example that showcases how fuzzing can be used in advanced ways to find issues and bugs that only appear 
  if functions are called in a specific order.