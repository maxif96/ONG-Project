# API Rest made for a NGO 
![](https://img.shields.io/github/languages/top/maxif96/ONG-Project)
![](https://img.shields.io/tokei/lines/github/maxif96/ONG-Project)
![](https://img.shields.io/github/last-commit/maxif96/ONG-Project?style=plastic)


This proyect is for a NGO who need to show what they do to the world.

## Package Structure

The package structure is made up of the followings packages:

![image](https://user-images.githubusercontent.com/87986166/189927036-096b4e5d-6d52-4898-821c-2551117082ad.png)


## Features

- **Really RESTful** - It follows the best practices

![carbon (2)](https://user-images.githubusercontent.com/87986166/189948701-4aa0a3fa-f917-492d-b267-5848f65897dc.png)

- **Protected endpoints, register and login** - Using Spring Security

![carbon (3)](https://user-images.githubusercontent.com/87986166/189949376-a841922e-3eae-463f-8be5-0699b1111343.png)

- **Transversal functionalities** - Using Spring AOP
- **Sending mails** - Using Sengrid
- **Images upload** - Using AWS
- **Documentated** - With Swagger
- **Integration tests** - Using JUnit5 and Mockito.

## How to use locally

Once you have downloaded the proyect and run it with Maven, you can access to the functionalities of the API with the graphic UI of Swagger.
Just put this url in your browser: http://localhost:8080/swagger-ui.html

You will see something like this:

![image](https://user-images.githubusercontent.com/87986166/189940857-91ca3f7e-8d96-4f6b-9a36-d59f5d321efe.png)

Now, you can access to the differents functionalities: As an admin, you can edit your organization data, add the news, write about you, your motivations, add new members, etcetera. As an user, you can comment on the news, you can also see your data, update or delete it, and more. 

No matter if you are admin or user, you need to register. How to do it?
You need to scroll down until you see the controller called 'user-auth-controller', click on it and some endpoints will unfold:

![image](https://user-images.githubusercontent.com/87986166/189944818-47adeca5-a4d0-490d-be6e-44bfffaeaeb5.png)

Then click on register and then click on try out: 

![image](https://user-images.githubusercontent.com/87986166/189945193-a788c9d5-39fb-49af-99f8-4c7d369493a5.png)

Next you have to complete the fields with your data and press Execute:

![image](https://user-images.githubusercontent.com/87986166/189945857-c1fb72f3-637a-450a-a5d9-17597dda7edc.png)

That will return the token with witch we can access to the rest of functionalities. We just have to copy and past the token on the Header Requeriments called 'Authorization' of the method. We need to write 'Bearer ' before. Like this:

![image](https://user-images.githubusercontent.com/87986166/189947397-c2c69527-fc8d-428c-b136-6778cb470773.png)

All endpoints have security validations to garantize the correct use of the API. For example, you can not register with an email that already exists in the database. You can not either put a password less than 8 characters, and more.

If you got here, thanks, and I hope you have understood how this API works.



