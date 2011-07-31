<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:blg="beleg" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" 
                version="2.0">

    <xsl:output method="xml"/>
    
    <xsl:variable name="ext">
        <xsl:element name="blg:teilsummen">
            <xsl:for-each select="/blg:beleg/blg:positionen/blg:position">
                <xsl:element name="blg:teilsumme">
                    <xsl:value-of select="blg:preis * blg:menge"/>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:variable>

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master  master-name="ersteseite"
		margin-right="1cm"   margin-left="1cm"
		margin-bottom="1cm"  margin-top="1cm"
		page-width="210mm"      page-height="297mm">
                    
                    <fo:region-body
                    margin-top="10cm"	margin-bottom="3cm"
                    margin-left="3cm"	margin-right="3cm" />
                    <fo:region-before region-name="ersteseite-before" extent="8cm" background-color="silver"/>
                    <fo:region-after extent="2cm" background-color="silver"/>
                    <fo:region-start extent="2cm" />
                    <fo:region-end extent="2cm" />
                                    
                </fo:simple-page-master>
                
                <fo:simple-page-master  master-name="folgeseiten"
		margin-right="1cm"   margin-left="1cm"
		margin-bottom="1cm"  margin-top="1cm"
		page-width="210mm"      page-height="297mm">
                    
                    <fo:region-body
                    margin-top="3cm"	margin-bottom="3cm"
                    margin-left="3cm"	margin-right="3cm" />
                    <fo:region-before extent="2cm" background-color="silver"/>
                    <fo:region-after extent="2cm" background-color="silver"/>
                    <fo:region-start extent="2cm" />
                    <fo:region-end extent="2cm" />
                                    
                </fo:simple-page-master>
			
                <fo:page-sequence-master master-name="rechnung">
                    <fo:repeatable-page-master-alternatives>
                        <fo:conditional-page-master-reference
                            master-reference="ersteseite"
                            page-position="first" />
                        <fo:conditional-page-master-reference 
                            page-position="rest" 
                            master-reference="restpage" />
                    </fo:repeatable-page-master-alternatives>
                </fo:page-sequence-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="rechnung">
                <fo:static-content flow-name="ersteseite-before">
                    <fo:table>
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="50%"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left">Hierlmeier GmbH</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right"> Belegnummer: 
                                        <xsl:value-of select="/beleg/belegnummer"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left">VetMed in Bavaria</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">Datum: 
                                        <xsl:value-of select="/beleg/datum"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                    
                    
                                      
                    <fo:table>
                        <fo:table-column column-width="25mm"/>
                        <fo:table-column column-width="50mm"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="right"></fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="left">Rechnung an:</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="right">Name:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="left">
                                        <xsl:value-of select="/beleg/kunde/vorname"/> 
                                        <xsl:value-of select="/beleg/kunde/nachname"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="right">Adresse:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="left">
                                        <xsl:value-of select="/beleg/kunde/adresse"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="right">Ort:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="left">
                                        <xsl:value-of select="/beleg/kunde/wohnort"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <xsl:if test="/beleg/kunde/telefonnummer != ''">
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block text-align="right">Tel.Nr.:</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="left">
                                            <xsl:value-of select="/beleg/kunde/telefonnummer"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:if>
                        </fo:table-body>

                    </fo:table>
                    
                </fo:static-content>
                
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block text-align="left"> Hierlmeier GmbH
                        <fo:page-number />
                    </fo:block>
                    <fo:block text-align="right"> Belegnummer 
                        <xsl:value-of select="/beleg/belegnummer"/>
                    </fo:block>
                    <fo:block text-align="right"> Datum: 
                        <xsl:value-of select="/beleg/datum"/>
                    </fo:block>
                </fo:static-content>

                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center"> Seite 
                        <fo:page-number />
                    </fo:block>
                </fo:static-content>
                
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="left"> Positionentable </fo:block>
                </fo:flow>
            </fo:page-sequence>
            
            
        </fo:root>
    </xsl:template>

</xsl:stylesheet>

