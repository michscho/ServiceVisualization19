# Service-Visualisation

**Summary**
> This tool was developed during a bachelor project. Its for modeling and visualizing services with Franca.

**Abstract**
> Background: Service-oriented architecture (SOA) is an architectural pat- tern for the administration, structuring, and handling of services in a com- pany. While monolithic architectures were predominant in the past, they have been increasingly replaced by SOA in recent years. In SOA business functions of low abstraction levels are combined to complex services. Over time many services accumulate in companies. Their growing number makes them more complex to understand and keep track of. Questions arise on how security-relevant they are, what real-time requirements exist, or how visibility between services should be. To answer these questions services are too generic, they lack distinctive characteristics. It is very time-consuming to make these thoughts on the level of services. A solution is to create service groups based on newly added service characteristics. Then the user will be able to assign properties to these services at the level of groups of different hierarchical levels.

> Aim: The goal of this bachelor thesis is to solve these problems by developing a visualization and modeling tool. The application will intro- duce automatically created abstract service groups based on well-defined characteristics. Properties can be defined for these service groups. It will work with the Framework Franca and visualizing these groups in different ways to better understand service-oriented systems. Modeling of the char- acteristics should feel intuitive, and the usual possibilities of a modeling tool will be available. Metrics can be used to compare different modeling approaches of the service groups.

> Method: Since the Franca Framework is too generic and there are no characteristics, the possibility to define characteristics must be added to Franca. Franca will also be expanded to include relationships between services. The extension is done using Xtext and EMF. JavaFX is then used to create a graphical user interface and the tool’s functionalities.

> Conclusion: The tool gives a good overview of the services. New service groups of different hierarchy levels can be created on the basis of service characteristics. This composition can be changed at will in the tool and
compared with each other using metrics. Further extensions are service relationships, quality of service attributes and service properties, all of which can be edited and supplemented in the tool. The use case done in this bachelor thesis shows good practical application utility.

**Execution**

*  For executable .jar file open: target/jfx/app/ or target/visualisation-jar-with-dependencies.jar
*  For installer open: target/jfx/native/
*  














Michael Schott at TUM