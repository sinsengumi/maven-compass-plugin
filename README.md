# maven-compass-plugin

## Overview

Maven plugin to compile in Compass.

This plugin uses Ruby and Sass and Compass. Specifically, please look at the site of [Compass](http://compass-style.org/).

For execute of this plugin, it is necessary for include Compass program in the PATH.


## Usage

    <build>
        <plugins>
            ....
            <plugin>
                <groupId>net.sinsengumi</groupId>
                <artifactId>maven-compass-plugin</artifactId>
                <version>0.0.1</version>
                <configuration>
                    <config>config.rb</config>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            ....
        </plugins>
    </build>

To run the compiler manually just execute: mvn compass:compile


### Or can set it individually without using config.rb.

    <build>
        <plugins>
            ....
            <plugin>
                <groupId>net.sinsengumi</groupId>
                <artifactId>maven-compass-plugin</artifactId>
                <version>0.0.1</version>
                <configuration>
                    <sassDir>src/main/webapp/sass</sassDir>
                    <cssDir>src/main/webapp/stylesheets</cssDir>
                    <outputStyle>compressed</outputStyle>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            ....
        </plugins>
    </build>

Specifically, please watch the manual of [Compass](http://compass-style.org/).


## All configuration options

+ debugInfo (Boolean) - Turns on sass's debuging information.
+ config (File) - Specify the location of the configuration file explicitly.
+ sassDir (File) - The source directory where you keep your sass stylesheets.
+ cssDir (File) - The target directory where you keep your css stylesheets.
+ imagesDir (File) - The directory where you keep your images.
+ javascriptsDir (File) - The directory where you keep your javascripts.
+ fontsDir (File) - The directory where you keep your fonts.
+ environment (String) - Use sensible defaults for your current environment. One of: development (default), production
+ outputStyle (String) - Select a CSS output mode. One of: nested, expanded, compact, compressed
+ noLineComments (Boolean) - Disable line comments.


## License

Apache License, Version 2.0  
<http://www.apache.org/licenses/LICENSE-2.0.txt>
