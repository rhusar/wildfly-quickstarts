/*
 * JBoss, by Red Hat.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.seam.forge.shell.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.forge.shell.test.command.MockOptionTestPlugin;
import org.jboss.seam.forge.test.AbstractShellTest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@RunWith(Arquillian.class)
public class ExecutionParserTest extends AbstractShellTest
{
   @Inject
   private MockOptionTestPlugin plugin;

   @Test
   public void testInvalidSuppliedOptionIsCorrected() throws Exception
   {
      String packg = "com.example.good.package";
      queueInputLines(packg);
      getShell().execute("motp suppliedOption --package bad_%package");
      assertEquals(packg, plugin.suppliedOption);
   }

   @Test
   public void testOmittedRequiredOptionIsFilled() throws Exception
   {
      String packg = "com.example.good.package";
      queueInputLines("another#$%bad($package", packg);
      getShell().execute("motp requiredOption");
      assertEquals(packg, plugin.requiredOption);
   }

   @Test
   public void testOmittedOptionalBooleanDefaultsToFalse() throws Exception
   {
      assertNull(plugin.booleanOptionOmitted);
      getShell().execute("motp booleanOptionOmitted");
      assertEquals(false, plugin.booleanOptionOmitted);
   }
}
