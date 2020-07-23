package com.slac.slac;

/**
 * Created by bezzob on 31.05.17.
 */

public class Questions
{
    public String question;
    public String rightAnswer;
    public String wrongAnswer1;
    public String wrongAnswer2;

    public Questions()
    {

    }

    public boolean checkIfRight(String answer)
    {
        return answer.equals(rightAnswer);
    }

    public String getRightAnswer()
    {
        return rightAnswer;
    }

    public String getWrongAnswer1()
    {
        return wrongAnswer1;
    }

    public String getWrongAnswer2()
    {
        return wrongAnswer2;
    }
}
