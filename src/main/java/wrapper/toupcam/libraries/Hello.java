package wrapper.toupcam.libraries;

import com.sun.jna.Library;

import wrapper.toupcam.models.MyStructure;

public interface Hello extends Library {
	
	void printHello();
	void printStructPointer(MyStructure st);
	void printStruct(MyStructure st);

}
