package org.underscore;

import org.underscore.functors.*;
import org.underscore.wrappers.$C;
import org.underscore.wrappers.$M;
import org.underscore.wrappers.$O;
import org.underscore.wrappers.$T;
import org.underscore.wrappers.$A;

import org.underscore.processor.IncludeInMain;
import javax.annotation.Generated;
import java.util.concurrent.TimeUnit;

import java.util.*;

public final class $ {

<#list methods as method>

@Generated("Underscore generator")${method}

</#list>

}