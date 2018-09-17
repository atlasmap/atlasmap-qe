export const xmlResources = {
    targetXMLSchema: `<xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">
       <xs:element name="TargetXmlMappingTestClass">
        <xs:complexType>
            <xs:sequence>
      <xs:element name="targetXmlDouble" type="xs:double"/>
      <xs:element name="targetXmlFloat" type="xs:float"/>
      <xs:element name="targetXmlInteger" type="xs:int"/>
      <xs:element name="targetXmlLong" type="xs:long"/>
      <xs:element name="targeXMmlShort" type="xs:short"/>
      <xs:element name="targetXmlString" type="xs:string" minOccurs="0"/>
      </xs:sequence>
      </xs:complexType>
      </xs:element>
</xs:schema>
    `,
    sourceXMLSchema: `<?xml version="1.0" encoding="UTF-8" ?>
    <xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">
      <xs:element name="SourceXmlMappingSchema">
        <xs:complexType>
          <xs:sequence>
            <xs:element name="sourceXMLBoolean" type="xs:boolean"/>
            <xs:element name="sourceXMLByte" type="xs:byte"/>
            <xs:element name="sourceXMLChar" type="xs:unsignedShort"/>
            <xs:element name="sourceXMLDate" type="xs:dateTime" minOccurs="0"/>
            <xs:element name="sourceXMLDouble" type="xs:double"/>
            <xs:element name="sourceXMLFloat" type="xs:float"/>
            <xs:element name="sourceXMLInteger" type="xs:int"/>
            <xs:element name="sourceXMLLong" type="xs:long"/>
            <xs:element name="sourceXMLShort" type="xs:short"/>
            <xs:element name="sourceXMLString" type="xs:string" minOccurs="0"/>
         </xs:sequence>
      </xs:complexType>
      </xs:element>
</xs:schema>
    `,

    sourceInstance: `<?xml version="1.0" encoding="UTF-8" ?><sourceXmlInstance>
        <sourceXmlBoolean>true</sourceXmlBoolean>
        <sourceXmlByte>A</sourceXmlByte>
        <sourceXmlChar>A</sourceXmlChar>
        <sourceXmlDate></sourceXmlDate>
        <sourceXmlDouble>100.100</sourceXmlDouble>
        <sourceXmlFloat>200.200</sourceXmlFloat>
        <sourceXmlInteger>300</sourceXmlInteger>
        <sourceXmlLong>400</sourceXmlLong>
        <sourceXmlShort>500</sourceXmlShort>
        <sourceXmlString>XmlString</sourceXmlString>
    </sourceXmlInstance>
    `
};
