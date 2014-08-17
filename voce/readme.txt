in order to use maven, include custom library into maven local repository

go to resources (cd comand ) and run this maven commands

mvn install:install-file -Dfile=cmu_us_kal-1.0.jar -DgroupId=com.sun.speech.freetts.en.us -DartifactId=cmu_us_kal -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=cmulex-1.0.jar -DgroupId=com.sun.speech.freetts.en.us -DartifactId=cmulex -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=en_us-1.0.jar -DgroupId=com.sun.speech.freetts.en -DartifactId=en_us -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=freetts-1.0.jar -DgroupId=com.sun.speech -DartifactId=freetts -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=jsapi-1.0.jar -DgroupId=javax.speech -DartifactId=jsapi -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=sphinx4-1.0.jar -DgroupId=edu.cmu.sphinx -DartifactId=sphinx4 -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz-1.0.jar -DgroupId=edu.cmu.sphinx.model.acoustic.WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz -DartifactId=WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz -Dversion=1.0 -Dpackaging=jar