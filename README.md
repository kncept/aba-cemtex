# aba-cemtex [![Build Status](https://travis-ci.org/kncept/aba-cemtex.svg?branch=master)](https://travis-ci.org/kncept/aba-cemtex)
CemtexABA Library - Australian Bankers Association format

If you require a custom integration, please contact nicholas.krul@gmail.com

# Why?
I've worked with these files many times, and every single company has a different 'standards free' implementation.
Not only that, but the implementation typically involves long, undelimited strings of values.
I needed an Industrial Grade implementation, so here it is.

# Quickstart
Use this dependency from maven central.
    `com.kncept.abacemtex:aba-cemtex:0.0.1`

Then use one of the `com.kncept.abacemtex.AbaCemtex.*` methods. It's that easy.

# Publishing
`./gradlew publishToMavenLocal`
or, set these env props: MAVEN_USERNAME, MAVEN_PASSWORD
`./gradlew publish`

### References
Breadcrumbs for partial information about file format and bank caveats.
* https://www.anz.com/Documents/AU/corporate/clientfileformats.pdf
* https://www.cemtexaba.com/aba-format/cemtex-aba-file-format-details
* http://tom0wu.blogspot.com/2014/05/australia-banks-integrate-with-aba-file.html
* https://www.commbank.com.au/support.digital-banking.importing-files-into-netbank.html

### verification tools
Either test data was sourced from here, or a provided 'validation tool' was used to validate output
* https://www.cemtexaba.com/
* http://callidex.com/?p=162
