<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:blg="beleg" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:date="http://exslt.org/dates-and-times"
                version="2.0">

    <xsl:output method="xml"/>
    

    
    <xsl:template match="/">
        
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master  master-name="ersteseite"
                    margin-right="1cm"   margin-left="1cm"
                    margin-bottom="1cm"  margin-top="1cm"
                    page-width="210mm"      page-height="297mm">
                    
                    <fo:region-body margin-top="9cm" margin-bottom="3cm" 
                        margin-right="7mm"   margin-left="7mm"/>
                    <fo:region-before region-name="ersteseite-before" extent="8cm" background-color="silver"/>
                    <fo:region-after extent="2cm" background-color="silver"/>
                    <fo:region-start extent="5mm" />
                    <fo:region-end extent="5mm" />
                                    
                </fo:simple-page-master>
                
                <fo:simple-page-master  master-name="folgeseiten"
		margin-right="1cm"   margin-left="1cm"
		margin-bottom="1cm"  margin-top="1cm"
		page-width="210mm"      page-height="297mm">
                    
                    <fo:region-body margin-top="3cm" margin-bottom="3cm" 
                        margin-right="7mm"   margin-left="7mm"/>
                    <fo:region-before extent="2cm" background-color="silver"/>
                    <fo:region-after extent="2cm" background-color="silver"/>
                    <fo:region-start extent="5mm" />
                    <fo:region-end extent="5mm" />
                                    
                </fo:simple-page-master>
			
                <fo:page-sequence-master master-name="rechnungdruck">
                    <fo:repeatable-page-master-alternatives>
                        <fo:conditional-page-master-reference
                            master-reference="ersteseite"
                            page-position="first" />
                        <fo:conditional-page-master-reference 
                            page-position="rest" 
                            master-reference="folgeseiten" />
                    </fo:repeatable-page-master-alternatives>
                </fo:page-sequence-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="rechnungdruck">
                <fo:static-content flow-name="ersteseite-before">
                    <fo:table>
                        <fo:table-column />
                        <fo:table-column />
                        <fo:table-column column-width="30mm"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left">Hierlmeier VetMed GmbH</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">Rechnungnummer:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">
                                        <xsl:value-of select="/rechnung/rechnungnummer"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left">Bester VetMed in Bavaria</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">Datum:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">
                                        <xsl:call-template name="date-formatter">
                                            <xsl:with-param name="date" select="/rechnung/datum" />
                                        </xsl:call-template>   
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                    
                    <fo:block  space-before="35mm" space-after="5mm" text-align="left">Rechnung an:</fo:block>
                                      
                    <fo:table>
                        <fo:table-column column-width="20mm"/>
                        <fo:table-column column-width="50mm"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left">Name:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="left">
                                        <xsl:value-of select="/rechnung/kunde/vorname"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="/rechnung/kunde/nachname"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left">Adresse:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="left">
                                        <xsl:value-of select="/rechnung/kunde/adresse"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left">Ort:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="left">
                                        <xsl:value-of select="/rechnung/kunde/wohnort"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <xsl:if test="/beleg/kunde/telefonnummer != ''">
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block text-align="left">Tel.Nr.:</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="left">
                                            <xsl:value-of select="/rechnung/kunde/telefonnummer"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:if>
                        </fo:table-body>

                    </fo:table>
                </fo:static-content>
                
                <fo:static-content flow-name="xsl-region-before">
                    <fo:table>
                        <fo:table-column />
                        <fo:table-column />
                        <fo:table-column column-width="30mm"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left">Hierlmeier VetMed GmbH</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">Rechnungnummer:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">
                                        <xsl:value-of select="/rechnung/rechnungnummer"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="left">Bester VetMed in Bavaria</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">Datum:</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">
                                        <xsl:call-template name="date-formatter">
                                            <xsl:with-param name="date" select="/rechnung/datum" />
                                        </xsl:call-template>   
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:static-content>

                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center"> Seite 
                        <fo:page-number />
                    </fo:block>
                </fo:static-content>
                
                <fo:flow flow-name="xsl-region-body">
                    
                    <fo:table>
                        <fo:table-column column-width="23mm"/>
                        <fo:table-column />                        
                        <fo:table-column column-width="23mm"/>
                        
                        <fo:table-header border-style="solid">
                            <fo:table-cell>
                                <fo:block font-weight="bold">Datum</fo:block>
                            </fo:table-cell>
                            <fo:table-cell>
                                <fo:block font-weight="bold">Beleg</fo:block>
                            </fo:table-cell>
                            <fo:table-cell>
                                <fo:block font-weight="bold" text-align="right">Betrag</fo:block>
                            </fo:table-cell>
                        </fo:table-header>   
                        
                        <fo:table-footer>
                            <fo:table-row>
                                <fo:table-cell column-number="1" number-columns-spanned="2">
                                    <fo:block text-align="right">Summe: </fo:block>
                                </fo:table-cell>
                                <fo:table-cell column-number="3">
                                    <fo:block text-align="right">
                                        <xsl:call-template name="currency-formatter">
                                            <xsl:with-param name="amount" select="/rechnung/summe" />
                                        </xsl:call-template>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <xsl:if test="/rechnung/kunde/mwst = 'true'" >
                                <fo:table-row>
                                    <fo:table-cell column-number="1" number-columns-spanned="2">
                                        <fo:block text-align="right">davon 20% MwSt: </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell column-number="3">
                                        <fo:block text-align="right">
                                            <xsl:call-template name="currency-formatter">
                                                <xsl:with-param name="amount" select="/rechnung/mwst" />
                                            </xsl:call-template>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell column-number="1" number-columns-spanned="2">
                                        <fo:block text-align="right">Nettosumme: </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell column-number="3">
                                        <fo:block text-align="right">
                                            <xsl:call-template name="currency-formatter">
                                                <xsl:with-param name="amount" select="/rechnung/netto" />
                                            </xsl:call-template>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>                    
                            </xsl:if>
                        </fo:table-footer>
                        
                        <fo:table-body>
                            <xsl:for-each select="/rechnung/rechnungbelege/beleg">
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block text-align="left">
                                            <xsl:call-template name="date-formatter">
                                                <xsl:with-param name="date" select="./datum" />
                                            </xsl:call-template>   
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="left">
                                            <xsl:value-of select="./belegtext"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="right">
                                            <xsl:call-template name="currency-formatter">
                                                <xsl:with-param name="amount" select="./offen" />
                                            </xsl:call-template>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:for-each>
                        </fo:table-body>
                                               
                    </fo:table>
                </fo:flow>
            </fo:page-sequence>
            
            
        </fo:root>
    </xsl:template>
    
    <xsl:template name="date-formatter">
        <xsl:param name="date" as="xs:date" required="yes"/>
        <xsl:value-of select="date:format-date($date, 'dd.MM.yyyy')"/>
    </xsl:template>
    
    <xsl:template name="currency-formatter">
        <xsl:param name="amount" as="xs:number" required="yes"/>
        <xsl:value-of select="format-number($amount, '#.00 €')"/>
    </xsl:template>

</xsl:stylesheet>

