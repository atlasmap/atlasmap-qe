export const xmlResources = {
    targetXMLSchema: `
<xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">
      <xs:element name="targetXMLBoolean" type="xs:boolean"/>
      <xs:element name="targetXMLByte" type="xs:byte"/>
      <xs:element name="targetXMLChar" type="xs:unsignedShort"/>
      <xs:element name="targetXMLDate" type="xs:dateTime" minOccurs="0"/>
      <xs:element name="targetXMLDouble" type="xs:double"/>
      <xs:element name="targetXMLFloat" type="xs:float"/>
      <xs:element name="targetXMLInteger" type="xs:int"/>
      <xs:element name="targetXMLLong" type="xs:long"/>
      <xs:element name="targeXMLtShort" type="xs:short"/>
      <xs:element name="targetXMLString" type="xs:string" minOccurs="0"/>
</xs:schema>
    `,

    sourceXMLSchema: `
    <xs:schema version="1.0" xmlns:xs="http://www.w3.org/2001/XMLSchema">
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
</xs:schema>
    `
}