package org.underscore;

import org.underscore.functors.*;
import org.underscore.wrappers.*;

import org.underscore.processor.IncludeInMain;
import javax.annotation.Generated;
import java.util.concurrent.*;
import java.io.IOException;

import java.util.*;

public final class $ {

<#list methods as method>

@Generated("Underscore generator")${method}

</#list>

}