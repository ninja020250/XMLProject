<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : productList.xsl
    Created on : July 4, 2019, 9:54 PM
    Author     : nhatc
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:ns2="http://www.monitors.com"
                xmlns:ns1="http://www.monitor.com">
    <xsl:output method="html" indent="yes"/>
    <xsl:param name="searchValue" />
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="ns2:ListMonitor">
        <section class="products-container">
            <xsl:for-each select="ns1:Monitor[contains(translate(ns1:description,'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), $searchValue)]">
                <div class="product-item"> 
                    <div class="title">
                        <xsl:value-of select="ns1:model" />
                    </div>
                     <div class="image">
                      <image src="{ns1:imgURL}" alt="hinh man hinh"/>
                    </div>
                </div>
            </xsl:for-each>
        </section>
      
    </xsl:template>
</xsl:stylesheet>
