# Lock

Implementation and benchmark of Tournament Peterson Lock and Bakery Lock.

Author:

- Hanlin He (hxh160630@utdallas.edu)
- Lizhong Zhang (lxz160730@utdallas.edu)

## Compile

Compilation and packaging are included in `makefile`. In `src` folder, the
following commands can be used.

    # create jar package in src folder.
    # TestLock.jar will be created.
    make

    # cleanup jar and class file.
    make clean

    # move jar to upper level.
    # TestLock.jar will be moved to upper level.
    make install

# Execution

The jar file was written to run benchmark with two locks directly. For a given
number of cores, the program would conduct experiments on the performance of
thread count ranging from 1 to the number of cores each with multiple times,
and output the average running of the experiments for different number of
threads. The critical section is only increment of a shared variable for
simplicity.

To execute, use command:

    java -jar <jar-file> <num of cores> <num of iteration> <num of experiment>

After execution, the program would output experiments results and summary in
format of latex plot/table that can be adopted if needed in latex.

# Example

An example execution from compile to execute is listed below.

    # Change to src folder for compilation.
    $ cd src

    # Make executable jar file.
    $ make
    javac -g Main.java
    jar cvfm TestLock.jar META-INF/MANIFEST.MF Main.class cs6301/github/io/lock/*.class cs6301/github/io/math/*.class cs6301/github/io/test/*.class
    added manifest
    adding: Main.class(in = 3956) (out= 2152)(deflated 45%)
    adding: cs6301/github/io/lock/Bakery$1.class(in = 1020) (out= 530)(deflated 48%)
    adding: cs6301/github/io/lock/Bakery.class(in = 2378) (out= 1242)(deflated 47%)
    adding: cs6301/github/io/lock/Lock.class(in = 149) (out= 128)(deflated 14%)
    adding: cs6301/github/io/lock/PetersonLock$1.class(in = 1062) (out= 534)(deflated 49%)
    adding: cs6301/github/io/lock/PetersonLock.class(in = 1400) (out= 745)(deflated 46%)
    adding: cs6301/github/io/lock/TournamentPetersonLock$1.class(in = 1132) (out= 542)(deflated 52%)
    adding: cs6301/github/io/lock/TournamentPetersonLock$PetersonLockWithLevel$1.class(in = 1777) (out= 737)(deflated 58%)
    adding: cs6301/github/io/lock/TournamentPetersonLock$PetersonLockWithLevel.class(in = 1448) (out= 553)(deflated 61%)
    adding: cs6301/github/io/lock/TournamentPetersonLock.class(in = 2960) (out= 1344)(deflated 54%)
    adding: cs6301/github/io/math/IntegerMath.class(in = 795) (out= 488)(deflated 38%)
    adding: cs6301/github/io/test/TestLock.class(in = 896) (out= 529)(deflated 40%)
    adding: cs6301/github/io/test/Timer.class(in = 1461) (out= 728)(deflated 50%)

    # Move jar file to upper level.
    $ make install
    mv TestLock.jar ..

    # Clean up.
    $ make clean
    rm -f Main.class cs6301/github/io/lock/*.class cs6301/github/io/math/*.class cs6301/github/io/test/*.class TestLock.jar

    # Change back to root folder.
    $ cd ..

    # Execute binary for 8 cores, running 100000 critical section for each
    # threads and do experiments 5 times for each parameter pair.
    $ java -jar TestLock.jar 8 100000 5
    Tournament lock benchmark:
    1 threads:	6 	3 	2 	1 	2 	2.80
    2 threads:	38 	33 	34 	33 	28 	33.20
    3 threads:	67 	59 	52 	58 	59 	59.00
    4 threads:	65 	72 	75 	67 	64 	68.60
    5 threads:	132 	117 	121 	113 	123 	121.20
    6 threads:	137 	139 	141 	148 	136 	140.20
    7 threads:	145 	145 	139 	155 	160 	148.80
    8 threads:	179 	169 	162 	149 	177 	167.20
    Final result for Tournament lock:
    plot format:
    (1,2.80)
    (2,33.20)
    (3,59.00)
    (4,68.60)
    (5,121.20)
    (6,140.20)
    (7,148.80)
    (8,167.20)
    table format:
    1 & 2.80 & 5 & 121.20
    2 & 33.20 & 6 & 140.20
    3 & 59.00 & 7 & 148.80
    4 & 68.60 & 8 & 167.20


    Bakery lock benchmark:
    1 threads:	20 	14 	9 	11 	3 	11.40
    2 threads:	49 	38 	36 	32 	34 	37.80
    3 threads:	62 	66 	57 	66 	61 	62.40
    4 threads:	94 	91 	92 	96 	95 	93.60
    5 threads:	127 	123 	124 	127 	124 	125.00
    6 threads:	171 	167 	176 	168 	168 	170.00
    7 threads:	221 	246 	214 	208 	227 	223.20
    8 threads:	297 	294 	279 	327 	294 	298.20
    Final result for Bakery lock:
    plot format:
    (1,11.40)
    (2,37.80)
    (3,62.40)
    (4,93.60)
    (5,125.00)
    (6,170.00)
    (7,223.20)
    (8,298.20)
    table format:
    1 & 11.40 & 5 & 125.00
    2 & 37.80 & 6 & 170.00
    3 & 62.40 & 7 & 223.20
    4 & 93.60 & 8 & 298.20

    Final result
    1 & 2.80 & 11.40 & 5 & 121.20 & 125.00 \\
    2 & 33.20 & 37.80 & 6 & 140.20 & 170.00 \\
    3 & 59.00 & 62.40 & 7 & 148.80 & 223.20 \\
    4 & 68.60 & 93.60 & 8 & 167.20 & 298.20 \\
