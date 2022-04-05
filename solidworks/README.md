# SolidWorks
* If you are not familiar with SoidWorks, please review the following materials before opening the files
* [SolidWorks Configuration Help Webpage](https://help.solidworks.com/2020/English/SolidWorks/sldworks/c_Configurations_Overview.htm)
* [SolidWorks Display States Help Webpage](https://help.solidworks.com/2020/English/SolidWorks/sldworks/c_display_states_configurations_parts.htm)
* Suppressing or removing any part of assembly will immediately cause errors to both assembly solid model and drawing
* If you are not an intermidate level SolidWorks user, do not modify the SolidWorks model. Instead, use it as is.

* The SolidWorks cad file (ASSY_IRT_PT_CAM.SLDASM) has 4 different configurations

* 45 DEGREE ASSY configuration
* DEFAULT ASSY configuration
* FIXED configuration
* ROTATABLE configuration

* 45 DEGREE ASSY configuration contains Exploded View 1 and 2
* Exploded View 1 is to show an assembly between rotating table and main tilt bracket
* Exploded View 1 must use "Display State- TILT TO RT TABLE ASSY"
* Exploded View 2 is to show an assembly between the assembled tilt bracket to base
* Exploded View 2 must use "Display State - TILT TO BASE ASSY"

* DEFAULT ASSY configuration
* Exploded View 3 is to show an assembly between the tilt bracket to tilt servo
* Exploded View 3 must use "Display Sate-TILT TO SERVO SIDE BRKT ASSY"
* Exploded View 4 is to show an assembly between combined previous assembly and battery base
* Exploded View 4 must use "Display State-MAIN BASE TO BATTERY BASE ASSY"

* FIXED configuration shows the entire assembly and tilt bracket is not rotatable

* ROTATABLE configuration shows the entire assembly and tilt bracket & phone craddle bracket are rotatable

* All fastener solid models are donwloaded from McMaster-Carr website (https://www.mcmaster.com/)
* Most fasteners are assembled using FEATURE DRIVEN PATTERN in SolidWorks
* Thus, suppressing or removing fasteners will cause errors.
* Please be familiar with SolidWorks FEATURE DRIVEN PATTERN assembly before modifying the solid model
* [SolidWorks Feature Driven Pattern Assembly Help Webpage](https://help.solidworks.com/2012/English/SolidWorks/sldworks/HIDD_DVE_COMPPAT_DERIVED.htm)

* PROTOTYPE DEVELOPMENT ARCHIVE folder has the entire history of solid model design from the begining to end
