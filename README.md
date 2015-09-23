#Facebook Photo Albums

Facebook Photo Album is an assignment from Social Orra Inc. This is a part of job interview. They ask me to implement a web application by using mainly Angular.js, Facebook4j and Play framework on Java. In order to create a user-friendly interface, I used Bootstrap css. 

##Versions
Angular.js = 1.4.6
Facebook4j = 2.3.0
Play framework = 2.2.6
SBT = 0.3.0


##Installation
Make sure that you have play and SBT installed on your PC or Mac.
I installed Play framework from Home brew with the following command
`brew install play22`
For SBT use that following command.
`brew install sbt`


At first, I installed Play Framework 2.3.9, but I encountered some JDK issues when I started play. So I downgraded to 2.2.6.

##Start server
Go to the project directory. Tap `play` to launch play console. Then enter `run` or `run your_port`. The project will be running on the default port 9000 or the port you specified after `run` command.


##Application manual

There are 4 views in this application. Login view, Album list view, Album detail view and Photo view. <br />

1. Login view allows user to signin by using their facebook account. By clicking on *Login with facebook* button, the page will redirect to Facebook website. The user need to approve the permissions that this application asked for. Three permissions are indispensable to guarantee the application fonctionalites, which are public profile, user photos and publish actions. <br /> 

Once the user approved all permissions, the page will be redirected to another page */getAlbums*

2. Here we can see the second view of this application the Album list view. On the view, the user can choose one album from a list of albums. The albums' name, created time, number of likes and the number of comments will be shown as well. 

3. When the user chose a album, the second view, Album detail view, will be presented. On this view, the photos in this corresponded album will be rendered along with the detail information of the album.

4. When user clicks on a photo, the photo view shows. The photo size is enlarged. Otherwise, user can add a comment on this photo. 

P.S. 
User can always go to previous page by clicking the *Back* button on the top of page.



## License

(The MIT License)

Copyright (c) 2011 Tom Gallacher &lt;<http:/ / www.tomg.co > & gt;

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files(the 'Software'), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and / or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS',
WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM,
DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE,
ARISING FROM,
OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


