This webapp redirects requests with HTTP 301 (Moved Permanently) response.

Works well when deployed to OpenShift as a stop-gap to send permanent
redirects for a domain name you've recently changed.

All configurable options are set in init-param values of
src/main/webapp/WEB-INF/web.xml. See that file for an explanation of
what can be configured and how to do it.

=== License

Copyright 2013 Red Hat Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.

You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
