# Create a Person class

## Class Variables (Private)

- String `firstname`
- String `lastname`
- int `birthmonth`
- int `birthday`
- int `birthyear`
- String ssn

## Constructor

The constructor should take in all values and assign them to their respective private class variables
> This class should probably follow a builder pattern but whatever

## Methods

Create public getters and setters for firstname and lastname:
`getFirstname`
`getLastname`
`setFirstname`
`setLastname`

Create a public getter method called `getBirthday`,
which will return a String composed of their birthday in month/day/year format.
For example, if `birthmonth=3`, `birthday=22`, `birthyear=2000`, it should return the String `"3/22/2000"`
> That's a really cursed format

Create a public method `verifySSN` that takes a String parameter that checks the
provided String against the person's SSN.
