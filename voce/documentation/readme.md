# links

- https://www.w3.org/TR/2000/NOTE-jsgf-20000605/
- https://developer.syn.co.in/tutorial/speech/jsgf-grammar.html
- https://software.intel.com/sites/landingpage/realsense/camera-sdk/v1.1/documentation/html/doc_speech_specifying_commands_using_java.html
- https://puneetk.com/basics-of-java-speech-grammar-format-jsgf

- https://git.freepbx.org/projects/CORE/repos/freetdm/browse/scripts/yes_no.gram?at=85b2d07f683129ab06e73631f2a27a2e10905cae
- https://fossies.org/linux/freeswitch/scripts/yes_no.gram
- https://github.com/goxr3plus/sphinx-5-Maven-Example/blob/master/resources/grammars/grammar.gram
- https://gitlab.eeecs.qub.ac.uk/40126401/csc4006-EdgeBenchmarking/blob/77640538a015a6bfe337b77832a4ccd5bb09ee8b/Experiments/PocketSphinx/sphinxbase/test/regression/test.gram
- https://gitlab.eeecs.qub.ac.uk/40126401/csc4006-EdgeBenchmarking/blob/77640538a015a6bfe337b77832a4ccd5bb09ee8b/Experiments/PocketSphinx/sphinxbase/test/regression/polite.gram
- https://scm.gforge.inria.fr/anonscm/svn/jtrans/sphinx4/src/apps/edu/cmu/sphinx/demo/helloworld/
- http://svn.roberttwomey.com/processing/voce/voceTedNelson_ReceiptPrinter/data/script.gram

# Grammar samples

#JSGF V1.0;

```
/**
 * JSGF Grammar
 */

grammar grammar;

public <feelings>  = ( how are you | say hello);
public <voices>  = ( change to voice one  | change to voice two | change to voice three );
public <amazing>  = ( say amazing | what day is today );
public <nervous>  = (who is your daddy | obey motherfucker | hey boss);
public <number> = ( zero | one | two | three | four | five | six | seven | nine | ten
                   | eleven | twelve | thirteen | fourteen | fifteen | sixteen | seventeen | eighteen | nineteen | twenty
                   | thirty | forty | fifty | sixty | seventy | eighty | ninety |
		            hundred | thousand | million | billion)+;                   
public <syntax> = <number>{1} (plus | minus | multiply | division){1} <number>{1};
```

---

```
#JSGF V1.0;

/**
  * JSGF Grammar for example
  */

grammar example;

<yes> = [ yes ];
<no> = [ no ];

public <results> = [ <yes> | <no> ];

```
---

```
- https://github.com/skerit/cmusphinx/blob/master/sphinx4/tests/performance/jsgf/integers.gram
```
---

```
#JSGF V1.0; /**  * JSGF Grammar for Hello World example  */ grammar polite; public <startPolite> = [please | kindly | could you | oh mighty computer]; public <endPolite> = [please | thanks | thank you]; public <allPolite> = (<startPolite> | <endPolite>)*;
```
---

```
#JSGF V1.0;

/**
 * JSGF Grammar for Hello World example
 */

grammar hello;

public <greet> = (Good morning | Hello) ( Bhiksha | Evandro | Paul | Philip | Rita | Will );
```
---
```
/**
 * JSGF Test Grammar file
 */


grammar script;
public <STOP> = ( stop | quit | exit );
```
