package com.example.toolinventorysystem.constants;

public interface AppConstants {
    String emailTemplet = """
            <!DOCTYPE html>
            <html>
            <head>
            <meta charset="UTF-8">
            <title>Invite Mail</title>
            <style>
            body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            }
                         
            h2 {
            color: #333;
            }
                         
            p {
            color: #666;
            }
                         
            ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            }
                         
            li {
            font-size: 14px;
            color: #999;
            margin-bottom: 5px;
            }
                         
            .message {
            background-color: #fff;
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
            }
            .login-button {
            text-align: center;
            margin-top: 20px;
            }
                         
            .login-button a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            font-size: 16px;
            text-decoration: none;
            border-radius: 5px;
            }
                         
            </style>
            </head>
            <body>
            <div class="message">
            <h2>Hello, {USER_NAME} </h2>
            <p>Here is the invite to join us as a {ROLE}! Below is your account information:</p>
            <ul>
            <li>Username: {EMAIL}</li>
            <li>Password: {PASSWORD}</li>
            </ul>
            <p>We welcome you!!</p>
            </div>
            </body>
            </html>
            """;
}
