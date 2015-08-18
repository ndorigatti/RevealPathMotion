# RevealPathMotion 

This repository provides an example of animating text and images between different activities by using SharedElementTransitions ( **API 21 and above** ).

Below an example of how the transition looks like.

![Screen](/doc/images/sample.gif)


## Components

The example provides two different animations that play together:

* **TextTransition** which animates text position, size and color changes smoothly (Thanks to alexloockwood), REF!

* **RevealTransition** which animates an ImageView transition by using a reveal animation, and an ArcMotion path.


### TextTransition

Coming soon...

### RevealTransition

Coming soon...


## ChangeLog

* First release, both animation working but with a lot of hardcoded parts.


Acknowledgements
--------------------

* Thanks to [Alex Lockwood][1] for [TextSizeTransition][2] classes and [Activity Transition][3] posts.
* Thanks to [George Mount][4] for the blog post and code about reveal transition [Reveal Activity Transitions][5].

Credits
-------

Author: Nicola Dorigatti

<a href="https://plus.google.com/u/0/+NicolaDorigatti">
  <img alt="Follow me on Google+"
       src="https://github.com/ndorigatti/RevealPathMotion/raw/master/doc/images/g+.png" />
</a>
<a href="https://twitter.com/ndorigatti">
  <img alt="Follow me on Twitter"
       src="https://github.com/ndorigatti/RevealPathMotion/raw/master/doc/images/twitter.png" />
</a>
<a href="http://it.linkedin.com/in/nicoladorigatti">
  <img alt="Follow me on LinkedIn"
       src="https://github.com/ndorigatti/RevealPathMotion/raw/master/doc/images/linkedin.png" />
</a>

License
-------

    Copyright (c) 2015 Nicola Dorigatti.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


---


 [1]: https://plus.google.com/+AlexLockwood
 [2]: https://github.com/alexjlockwood/custom-lollipop-transitions/blob/master/app%2Fsrc%2Fmain%2Fjava%2Fcom%2Falexjlockwood%2Ftransitions%2Fcustom%2FTextSizeTransition.java
 [3]: http://www.androiddesignpatterns.com/2014/12/activity-fragment-transitions-in-android-lollipop-part1.html
 [4]: https://halfthought.wordpress.com/
 [5]: https://halfthought.wordpress.com/2014/12/02/reveal-activity-transitions/
