JFLAGS = -g
JC = javac
JARFLAGS = cvfm
JAR = jar

TL = TestLock

OBJECTS = Main.class cs6301/github/io/lock/*.class cs6301/github/io/math/*.class cs6301/github/io/test/*.class
META = META-INF/MANIFEST.MF

INSTALL = mv

TestLock: $(OBJECTS)
	$(JAR) $(JARFLAGS) $(TL).jar $(META) $(OBJECTS)

Main.class: Main.java
	$(JC) $(JFLAGS) Main.java

cs6301/github/io/lock/*.class:

cs6301/github/io/math/*.class:

cs6301/github/io/test/*.class:

clean:
	$(RM) $(OBJECTS) $(TL).jar

install:
	$(INSTALL) $(TL).jar ..

distclean: clean
	$(RM) ../$(TL).jar

.PHONY: all install clean
